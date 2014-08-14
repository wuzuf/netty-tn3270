%%{
    machine tn3270;

    ##########
    # TELNET
    ##########

    tn_iac  = 0xff;
    tn_se   = 0xf0;
    tn_nop  = 0xf1;
    tn_dm   = 0xf2;
    tn_brk  = 0xf3;
    tn_ip   = 0xf4;
    tn_ao   = 0xf5;
    tn_ayt  = 0xf6;
    tn_ec   = 0xf7;
    tn_el   = 0xf8;
    tn_ga   = 0xf9;
    tn_sb   = 0xfa;
    tn_will = 0xfb;
    tn_wont = 0xfc;
    tn_do   = 0xfd;
    tn_dont = 0xfe;
    tn_eor  = 0xef;
      
    tn_command = tn_nop | tn_brk | tn_ip | tn_ao | tn_ayt | tn_ec | tn_el | tn_ga;
    tn_command_arg = tn_will | tn_wont | tn_do | tn_dont;
    tn_commmand_subneg = tn_sb;

    tn_plain_text = (^tn_iac | tn_iac tn_iac);
      
    tn_basic_command  = tn_iac tn_command @tn_command;
    tn_arg_command    = tn_iac tn_command_arg >tn_argcommand any @tn_argcommand_arg ;
    tn_subneg_command = tn_iac tn_commmand_subneg any @tn_subneg;

    tn_iac_sequence = ( tn_basic_command | tn_arg_command | tn_subneg_command );

    ##########
    # TN3270E
    ##########

    tn3270_tn3270e             = 0x28;
    tn3270_associate           = 0x00;
    tn3270_connect             = 0x01;
    tn3270_device_type         = 0x02;
    tn3270_functions           = 0x03;
    tn3270_is                  = 0x04;
    tn3270_reason              = 0x05;
    tn3270_reject              = 0x06;
    tn3270_request             = 0x07;
    tn3270_send                = 0x08;
    tn3270_conn_partner        = 0x00;
    tn3270_device_in_use       = 0x01;
    tn3270_inv_associate       = 0x02;
    tn3270_inv_name            = 0x03;
    tn3270_inv_device_type     = 0x04;
    tn3270_type_name_error     = 0x05;
    tn3270_unknown_error       = 0x06;
    tn3270_unsupported_req     = 0x07;
    tn3270_bind_image          = 0x00;
    tn3270_data_stream_ctl     = 0x01;
    tn3270_responses           = 0x02;
    tn3270_scs_ctl_codes       = 0x03;
    tn3270_sysreq              = 0x04;

    tn3270_ascii = [A-Z0-9_\-];

    tn3270_resource_name = tn3270_ascii{1,8} >tn3270_resource_name $tn3270_name  %tn3270_name_end;
    tn3270_device_types = tn3270_ascii{1,15} >tn3270_device_type   $tn3270_name  %tn3270_name_end;
    tn3270_device_name = tn3270_ascii{1,8}   >tn3270_device_name   $tn3270_name  %tn3270_name_end;
    tn3270_reason_code = tn3270_conn_partner | tn3270_inv_associate | tn3270_inv_name | tn3270_inv_device_type | tn3270_type_name_error | tn3270_unknown_error | tn3270_unsupported_req;
    tn3270_function_list = ( tn3270_bind_image | tn3270_data_stream_ctl | tn3270_responses | tn3270_scs_ctl_codes | tn3270_sysreq ){0,20} >tn3270_functions_list  $tn3270_name  %tn3270_name_end;

    # subnegociation
    tn3270_subneg_send_device_type = tn3270_send . tn3270_device_type %tn3270_send_device_type;
    tn3270_subneg_device_type_request = tn3270_device_type . tn3270_request . tn3270_device_types . ( tn3270_connect . tn3270_resource_name | tn3270_associate . tn3270_device_name ) %tn3270_device_type_request;
    tn3270_subneg_device_type_is = tn3270_device_type . tn3270_is . tn3270_device_types . tn3270_connect . tn3270_device_name %tn3270_device_type_is;
    tn3270_subneg_device_type_reject = tn3270_device_type . tn3270_reject . tn3270_reason . tn3270_reason_code %tn3270_device_type_reject;
    tn3270_subneg_function_request = tn3270_functions . tn3270_request . tn3270_function_list %tn3270_function_request;
    tn3270_subneg_function_is = tn3270_functions . tn3270_is . tn3270_function_list %tn3270_function_is;
    tn3270_subneg_list = tn3270_subneg_send_device_type
                       | tn3270_subneg_device_type_request
                       | tn3270_subneg_device_type_is
                       | tn3270_subneg_device_type_reject
                       | tn3270_subneg_function_request
                       | tn3270_subneg_function_is;
    
    tn3270_subneg := tn3270_subneg_list . tn_iac . tn_se @tn_subneg_end;

    tn3270_arg = any.any @tn3270_endarg;
    tn3270_args := tn3270_arg+;
    
    tn3270_erase_write = (0x05 | 0xf5) @tn3270_erase_write;
    tn3270_write = (0x01 | 0xf1) @tn3270_write;

    tn3270_command = (tn3270_write | tn3270_erase_write | 0x7e | 0x6f | 0xf6 | 0x6e | 0xf2 | 0xf3) ;
    tn3270_wcc = any @tn3270_wcc;
    tn3270_aid = 0x7d @tn3270_aid;
    tn3270_addr = any{2} >tn3270_addr  $tn3270_name  %tn3270_name_end;

    # orders
    tn3270_sba = 0x11 . tn3270_addr @tn3270_sba;
    tn3270_sf = 0x1d . any @tn3270_sf;
    tn3270_ic = 0x13 @tn3270_ic;
    tn3270_eua = 0x12 . tn3270_addr @tn3270_eua;
    tn3270_pt = 0x05 @tn3270_pt;
    tn3270_sfe = 0x29 . any @tn3270_sfe;
    tn3270_ra = 0x3c . tn3270_addr . any @tn3270_ra;
        
    tn3270_order = ( tn3270_sba | tn3270_sf | tn3270_ic | tn3270_eua | tn3270_pt | tn3270_sfe | tn3270_ra ) >tn3270_endtxt %tn3270_starttxt;
    tn3270_plain_text = (any - (0x11 | 0x1d | 0x12 | 0x05 | 0x29 | 0x3c | tn_iac)) +;
    tn3270_content = (tn3270_order | tn3270_plain_text) *;
    tn3270_header = any {5} @tn3270_header;
    tn3270_data = ( ( (tn3270_command . tn3270_wcc) | (tn3270_aid . tn3270_addr) ) . tn3270_content);
    tn3270_message = tn3270_header . tn3270_data . tn_iac @tn3270_message;
    tn3270 = ( tn_iac_sequence | tn3270_message . tn_eor )*  $err(error);

}%%