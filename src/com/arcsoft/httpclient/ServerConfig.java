
package com.arcsoft.httpclient;

/**
 * @author pjq0274@arcsoft.com
 * @date 2011-4-22
 */

public class ServerConfig {
    public static final String HTTP_SERVER_REGISTER_URL = "http://180.168.105.166:8012/AASService.asmx/Register";
    public static final String HTTPS_SERVER_REGISTER_URL = "http://180.168.105.166:8012/AASService.asmx/Register";

    public static final String SOAP_TMP = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">  <soap:Body>    <Register xmlns=\"http://www.arcsoft.com/\">      <nType>int</nType>      <strKey>string</strKey>      <strPassword>string</strPassword>    </Register>  </soap:Body></soap:Envelope>";

    public ServerConfig() {
        // TODO Auto-generated constructor stub
    }

}
