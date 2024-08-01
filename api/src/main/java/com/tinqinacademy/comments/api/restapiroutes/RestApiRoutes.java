package com.tinqinacademy.comments.api.restapiroutes;

public class RestApiRoutes {
    private static final String API_V1 = "/api/v1";
    //containing api_v1
    private static final String API_V1_HOTEL = API_V1 + "/hotel";
    private static final String API_V1_SYSTEM = API_V1 + "/system";
    //without api_v1
    private static final String ROOM_ID_COMMENT = "/{roomId}/comment";
    private static final String COMMENT_COMMENT_ID = "/comment/{commentId}";
    //system
    public static final String SYSTEM_DELETE_COMMENT = API_V1_SYSTEM + COMMENT_COMMENT_ID;
    public static final String SYSTEM_EDIT_COMMENT = API_V1_SYSTEM + COMMENT_COMMENT_ID;
    //hotel
    public static final String HOTEL_GET_COMMENTS = API_V1_HOTEL + ROOM_ID_COMMENT;
    public static final String HOTEL_POST_COMMENT = API_V1_HOTEL + ROOM_ID_COMMENT;
    public static final String HOTEL_UPDATE_COMMENT = API_V1_HOTEL + COMMENT_COMMENT_ID;
}
