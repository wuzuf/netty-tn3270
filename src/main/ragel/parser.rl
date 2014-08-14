%%{
 	machine parser;

    action error { parser.listener.emit(new ParsingError(state.data, state.position)); }

    action tn_command        { parser.listener.emit(new Command((byte)fc));}
    action tn_argcommand     { state.command = (byte)fc;}
    action tn_argcommand_arg { parser.listener.emit(new ArgCommand(state.command, (byte)fc));}
    
    action tn3270_function_request    { parser.listener.emit(new FunctionsRequest(state.functionsList)); }
    action tn3270_function_is         { parser.listener.emit(new FunctionsIs(state.functionsList)); }
    action tn3270_send_device_type    { parser.listener.emit(new SendDeviceType()); }
    action tn3270_device_type_request { parser.listener.emit(new DeviceTypeRequest(state.deviceType, state.deviceName, state.resourceName)); }
    action tn3270_device_type_is      { parser.listener.emit(new DeviceTypeIs(state.deviceType, state.deviceName)); }
    action tn3270_device_type_reject  { parser.listener.emit(new DeviceTypeReject(fc)); }

	# Server -> Terminal
    action tn3270_write          { parser.listener.emit(new SOM()); parser.listener.emit(new Write()); }
    action tn3270_erase_write    { parser.listener.emit(new SOM()); parser.listener.emit(new EraseWrite()); }
    
    # Terminal -> Server
    action tn3270_aid        { parser.listener.emit(new SOM()); parser.listener.emit(new AID(fc)); }
    
    # WCC
    action tn3270_wcc        { parser.listener.emit(new WCC(fc)); }
    
    # TN3270 Orders
    action tn3270_sba        { parser.listener.emit(new SBA(state.GetAddr())); }
    action tn3270_eua        { parser.listener.emit(new EUA(state.GetAddr())); }
    action tn3270_ic         { parser.listener.emit(new IC()); }
    action tn3270_pt         { parser.listener.emit(new PT()); }
    action tn3270_sf         { parser.listener.emit(new SF(fc)); }
    action tn3270_ra         { parser.listener.emit(new RA(state.GetAddr(), fc)); }
    action tn3270_sfe        { parser.listener.emit(new SFE(fc)); state.saveCount(fc); fcall tn3270_args; }
                             
    action tn3270_message    { parser.endTxt(); parser.listener.emit(new EOM()); }
    action tn3270_starttxt   { parser.startTxt(); }
    action tn3270_endtxt     { parser.endTxt(); }
 
    action tn3270_resource_name {
        state.saveResourceName();
    }
    action tn3270_addr {
    	state.saveAddr();
    }
    action tn3270_device_name {
        state.saveDeviceName();
    }
    action tn3270_device_type {
        state.saveDeviceType();
    }
    action tn3270_functions_list {
        state.saveFunctionList();
    }
    action tn3270_name {
    	state.appendName(fc);
    }
    
    action tn3270_name_end {
        state.endName();
    }

    
    action tn3270_header {}

    action tn3270_endarg { state.count--; if(state.count == 0) { fret; } }
    action tn_subneg { fcall tn3270_subneg; }
    action tn_subneg_end { fret; }
  
	include tn3270 "tn3270.rl";
    
    access state.;
    variable p state.position;
    variable pe state.length;
    getkey state.data.getUnsignedByte(state.position);
    
  	main := tn3270;
}%%

package fr.wuzuf.netty.handlers.codec.tn3270;

import io.netty.buffer.ByteBuf;

import fr.wuzuf.netty.handlers.codec.tn3270.command.*;
import fr.wuzuf.netty.handlers.codec.tn3270.handshake.*;
import fr.wuzuf.netty.handlers.codec.tn3270.order.*;
import fr.wuzuf.netty.handlers.codec.tn3270.tn.*;

public final class Parser {
	State state;
	ParserListener listener;
	
	void startTxt() {
		this.state.starttxt = this.state.position;
	}
	
	void endTxt() {
	    if (this.state.starttxt != -1 && this.state.starttxt < this.state.position) {
	    	this.listener.emit(new TEXT(this.state.data.slice(this.state.starttxt, this.state.position-this.state.starttxt)));
	    }
	    this.state.starttxt = -1;
	}
	
	void captureTxt() {
	    if (this.state.starttxt != -1 && this.state.starttxt < this.state.position) {
	    	this.listener.emit(new TEXT(this.state.data.slice(this.state.starttxt, this.state.position-this.state.starttxt)));
	        this.state.starttxt = 0;
	    }
	}
	
	Parser(ParserListener listener) {
	    this.state = new State();
	    this.listener = listener;
	    %% write init;
	}
	
	void parse(ByteBuf buffer) {
		this.state.position = 0;
	    this.state.data = buffer;
	    this.state.length = buffer.readableBytes();
	    int eof = 0;
	    
	    State state = this.state;
	    Parser parser = this;
	
	    %% write exec;
	
	    // Store any pending text
	    parser.captureTxt();
	    
	    return;
	}
	
	int finish () {
	    if ( state.cs == parser_error ) {
	        return -1;
	    }
	    if ( state.cs >= parser_first_final ) {
	        return 1;
	    }
	    return 0;
	}
	
	class State {
		ByteBuf data;
	    int length;
	    int position;
	    int[] stack;
	    int count;
	    int top;
	    int ts;
	    int cs;
	    byte[] name;
	
	    int starttxt;
	    
	    byte command;
	    int idx;
	    byte[] addr; 
	    short[] resourceName;
	    short[] deviceName;
	    short[] deviceType;
	    short[] functionsList;
	    
	    
		State() {
			this.starttxt = -1;
			this.cs = 0;
			this.ts = 0;
			this.position = 0;
			this.stack = new int[10];
			this.addr = new byte[2];
		}
	    
	    int GetAddr() {
	        if ((state.addr[0] & 0xC0) == 0x00)  {
	            return ((int)(state.addr[0] & 0x3F) << 8) | (int)(state.addr[1]);
	        }
	        return ((int)(state.addr[0] & 0x3F) << 6) | (int)(state.addr[1] & 0x3F);
	    }

		public void saveCount(short b) {
			// TODO Auto-generated method stub
			
		}

		public void endName() {
			// TODO Auto-generated method stub
			
		}

		public void appendName(short b) {
			// TODO Auto-generated method stub
			
		}

		public void saveFunctionList() {
			// TODO Auto-generated method stub
			
		}

		public void saveDeviceType() {
			// TODO Auto-generated method stub
			
		}

		public void saveDeviceName() {
			// TODO Auto-generated method stub
			
		}

		public void saveAddr() {
			// TODO Auto-generated method stub
			
		}

		public void saveResourceName() {
			// TODO Auto-generated method stub
			
		}
	}
  
  	%% write data;
}