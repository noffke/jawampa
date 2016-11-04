package ws.wamp.jawampa.transport.netty;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.ssl.SslContext;
import ws.wamp.jawampa.connection.IWampClientConnectionConfig;

public class NettyWampConnectionConfig implements IWampClientConnectionConfig {

    static final int DEFAULT_MAX_FRAME_PAYLOAD_LENGTH = 65535;

    SslContext sslContext;
    int maxFramePayloadLength;
    private HttpHeaders additionalHeaders;

    NettyWampConnectionConfig(SslContext sslContext, int maxFramePayloadLength, HttpHeaders additionalHeaders) {
        this.sslContext = sslContext;
        this.maxFramePayloadLength = maxFramePayloadLength;
        this.additionalHeaders = additionalHeaders;
    }

    /**
     * the SslContext which will be used to create Ssl connections to the WAMP
     * router. If this is set to null a default (unsecure) SSL client context will be created
     * and used. 
     */
    public SslContext sslContext() {
        return sslContext;
    }

    public int getMaxFramePayloadLength() {
        return maxFramePayloadLength;
    }

    public HttpHeaders getAdditionalHeaders() {
        return additionalHeaders;
    }

    /**
     * Builder class that must be used to create a {@link NettyWampConnectionConfig}
     * instance.
     */
    public static class Builder {

        SslContext sslContext;
        int maxFramePayloadLength = DEFAULT_MAX_FRAME_PAYLOAD_LENGTH;
        private HttpHeaders additionalHeaders = HttpHeaders.EMPTY_HEADERS;


        /**
         * Allows to set the SslContext which will be used to create Ssl connections to the WAMP
         * router. If this is set to null a default (unsecure) SSL client context will be created
         * and used. 
         * @param sslContext The SslContext that will be used for SSL connections.
         * @return The {@link Builder} object
         */
        public Builder withSslContext(SslContext sslContext) {
            this.sslContext = sslContext;
            return this;
        }

        public Builder withMaxFramePayloadLength(int maxFramePayloadLength){
            if ( maxFramePayloadLength <= 0 ){
                throw new IllegalArgumentException("maxFramePayloadLength parameter cannot be negative");
            }
            this.maxFramePayloadLength = maxFramePayloadLength;
            return this;
        }

        /**
         * Allows to set additional http headers to be sent in the http websocket handshake.
         * @param httpHeaders Additional headers
         * @return The {@link Builder} object
         */
        public Builder withHttpHeaders(HttpHeaders httpHeaders){
            this.additionalHeaders = httpHeaders;
            return this;
        }

        public NettyWampConnectionConfig build() {
            return new NettyWampConnectionConfig(sslContext, maxFramePayloadLength, additionalHeaders);
        }
    }
}
