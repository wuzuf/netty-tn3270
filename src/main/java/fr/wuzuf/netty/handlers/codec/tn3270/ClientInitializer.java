/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package fr.wuzuf.netty.handlers.codec.tn3270;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Creates a newly configured {@link ChannelPipeline} for a new channel.
 */
public class ClientInitializer extends ChannelInitializer<SocketChannel> {


    public ClientInitializer() {
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();

        // Logging handler useful for troubleshooting
        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
        
        // TN3270 Decoders and encoders
        pipeline.addLast(new Codec());

        // Handshake
        pipeline.addLast(new HandshakeMessageHandler());
        
        // Reply to timing marks
        pipeline.addLast(new TimingMarkHandler());
        
        // TN3270 Decoding
        pipeline.addLast(new DataMessageHandler());
        
        // End of Message
        
        
    }
}
