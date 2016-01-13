package com.cocoonshu.network;

/**
 * Http status code
 * @author Cocoonshu
 * @date   2016-01-06
 */
public enum HttpCode {
    // Defination
    Unknown                     (-1, "Unknown"),
    Undefined                   (0,  "Undefined"),
    
    // Message
    Continue                    (100, "Continue"),
    SwitchingProtocols          (101, "SwitchingProtocols"),
    Processing                  (102, "Processing"),
    
    // Success
    OK                          (200, "OK"),
    Created                     (201, "Created"),
    Accepted                    (202, "Accepted"),
    NonAuthoritativeInformation (203, "NonAuthoritativeInformation"),
    NoContent                   (204, "NoContent"),
    ResetContent                (205, "ResetContent"),
    PartialContent              (206, "PartialContent"),
    MultiStatus                 (207, "MultiStatus"),
    
    // Redirect
    MultipleChoices             (300, "MultipleChoices"),
    MovedPermanently            (301, "MovedPermanently"),
    MoveTemporarily             (302, "MoveTemporarily"),
    SeeOther                    (303, "SeeOther"),
    NotModified                 (304, "NotModified"),
    UseProxy                    (305, "UseProxy"),
    SwitchProxy                 (306, "SwitchProxy"),
    TemporaryRedirect           (307, "TemporaryRedirect"),
    
    // Request error 
    BadRequest                  (400, "BadRequest"),
    Unauthorized                (401, "Unauthorized"),
    PaymentRequired             (402, "PaymentRequired"),
    Forbidden                   (403, "Forbidden"),
    NotFound                    (404, "NotFound"),
    MethodNotAllowed            (405, "MethodNotAllowed"),
    NotAcceptable               (406, "NotAcceptable"),
    ProxyAuthenticationRequired (407, "ProxyAuthenticationRequired"),
    RequestTimeout              (408, "RequestTimeout"),
    Conflict                    (409, "Conflict"),
    Gone                        (410, "Gone"),
    LengthRequired              (411, "LengthRequired"),
    PreconditionFailed          (412, "PreconditionFailed"),
    RequestEntityTooLarge       (413, "RequestEntityTooLarge"),
    RequestURITooLong           (414, "RequestURITooLong"),
    UnsupportedMediaType        (415, "UnsupportedMediaType"),
    RequestedRangeNotSatisfiable(416, "RequestedRangeNotSatisfiable"),
    ExpectationFailed           (417, "ExpectationFailed"),
    TooManyConnectionsFromYour  (418, "ThereAreTooManyConnectionsFromYourInternetAddress"),
    UnprocessableEntity         (419, "UnprocessableEntity"),
    Locked                      (420, "Locked"),
    FailedDependency            (421, "FailedDependency"),
    UnorderedCollection         (422, "UnorderedCollection"),
    UpgradeRequired             (423, "UpgradeRequired"),
    RetryWith                   (424, "RetryWith"),
    
    // Server error
    InternalServerError         (500, "InternalServerError"),
    NotImplemented              (501, "NotImplemented"),
    BadGateway                  (502, "BadGateway"),
    ServiceUnavailable          (503, "ServiceUnavailable"),
    GatewayTimeout              (504, "GatewayTimeout"),
    HTTPVersionNotSupported     (505, "HTTPVersionNotSupported"),
    VariantAlsoNegotiates       (506, "VariantAlsoNegotiates"),
    InsufficientStorage         (507, "InsufficientStorage"),
    BandwidthLimitExceeded      (509, "BandwidthLimitExceeded"),
    NotExtended                 (510, "NotExtended"),
    
    // Response error
    UnparseableResponseHeaders  (600, "UnparseableResponseHeaders");
    
    private String mMessage = null;
    private int    mCode    = 0;
    
    private HttpCode(int code, String message) {
        mCode    = code;
        mMessage = message;
    }
    
    public final String getStatusString() {
        return mMessage;
    }
    
    public final int getStatusCode() {
        return mCode;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s", mCode, mMessage);
    }
    
    public static HttpCode valueOf(int responseCode) {
        for (HttpCode item : HttpCode.values()) {
            if (item.mCode == responseCode) {
                return item;
            }
        }
        return Unknown;
    }
}
