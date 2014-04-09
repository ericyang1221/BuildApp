package com.arvatoservice.arvatoshopping.utils;

public class Constants {
	public static final String BMAP_KEY = "797123EAFCAB07DD22EE68F85945FAD59DBD84FE";
	public static final String DOUBAN_API_KEY = "05d7b1737baf8c9c1187145a730ea5d4";

	public final static String DOUBAN_BOOK_INFO_SERVER = "http://api.douban.com/book/subject/isbn/";
	public final static String NAQUMAI_PRICE_SERVER = "http://www.naqumai.com/book/api?isbn=?&source=?";

	public final static int DATABASE_VERSION = 8;

	public final static int BAIDU_PUSH_NOTIFICATION_ID = 1;

	public final static int PUSH_MSG_NO_RID = -1;
	public final static int PUSH_MSG_TYPE_INFO = 1;
	public final static int PUSH_MSG_TYPE_PRODUCT = 2;
	public final static int PUSH_MSG_TYPE_COUPON = 3;
	public final static String PUSH_INTENT_DIRECTION_KEY = "direction";
	public final static int PUSH_INTENT_DIRECTION_CENTER = 0;
	public final static int PUSH_INTENT_DIRECTION_BOTTOM = 3;
	
	// Configure paras.
	public static final int SHARE_ARVATOURL_LENTH_LIMIT = 150;
	public static final int CACHE_FOLDER_SIZE_LIMIT_IN_MB = 50;

	public static final int FILE_DECODE_PARA_SIZE = 200;

	public static final double MAX_NATIVE_MEMORY_LIMIT_ARG = 0.5;
	public static long MAX_NATIVE_MEMORY_LIMIT = (long) (16 * 1024 * 1024 * MAX_NATIVE_MEMORY_LIMIT_ARG);

	public static final long ANIM_DURATION = 500;

	public static final String SCAN_HISTORY_MARK_GRAPH_RECOGNIZE = "SCAN_HISTORY_MARK_GRAPH_RECOGNIZE";
	public static final String SCAN_HISTORY_MARK_BOOK = "SCAN_HISTORY_MARK_BOOK";
	public static final String SCAN_HISTORY_MARK_QR = "";

	public static final String ARVATO_ROOT = "/Arvato/buildapp/";
	public static final String CACHE_PATH = "cache/";
	public static final String L2_CACHE_PATH = "cache/L2/";
	public static final String TEMP_PATH = "temp/";
	public static final String DOWNLOAD_PATH = "download/";
	public static final String CRASH_PATH = "log/";
	public static final String Video_PATH = "cache/video/";

	public static final String BASE_JINGDONG_URL = "http://m.360buy.com/product/";
	public static final String BASE_STORENO1_URL = "http://m.yihaodian.com/product/";

	public static final int PREVIOUS_LIST_MAXCOUNT = 19;
	public static final int CAMERA_LANDSCAPE_SDK_CODE = 8;

	public static final int LOADING_DIALOG = 1001;
	public static final int PAYING_DIALOG = 1002;
	public static final int NAVIGATION_LOADING_DIALOG = 1003;
	public static final int COUPON_ACTIVE_DIALOG = 1004;
	public static final int RECOGNIZING_DIALOG = 1005;
	public static final int CHECKUPDATING_DIALOG = 1007;

	public static final String APPLICATION_STATE_SETTING = "setting";// �����ؼ��ʱ����ת������ҳ��
	public static final String APPLICATION_STATE_HOME = "home";// �����ؼ��ʱ����ת����ҳ
	public static final String APPLICATION_STATE_CAR = "car";// �����ؼ��ʱ����ת�����ﳵ
	public static final String APPLICATION_STATE_ORDER = "order";// �����ؼ��ʱ����ת�����ﳵ
	public static final String APPLICATION_STATE_RECOMMEND = "recommend";// �����ؼ��ʱ����ת���Ƽ�
	public static final String APPLICATION_STATE_GOBACK_HOME = "gohome";//

	public static final String BASE_URL = "http://118.145.20.146/buildapp/";
	public static final String COUPON_URL = "api/product/list?type=0&pageNum=1&pageSize=10&brandId=71";

	public static final String BASECHECK_URL = BASE_URL + "apix/";
	public static String HTTP_URL = BASE_URL + "api/";
//	public static String IMG_URL = "http://118.145.20.146/buildapp";
	public static String IMG_URL = "";
	public static String THEME_URL = BASE_URL + "theme/";
	public static String BASE_ICON_URL = BASE_URL + "/icon/";
	public static String TRACK_URL = "http://report.arvatopd.com/mobileApi";
	// public static String GRAPH_RECOGNIZE_URL =
	// "http://192.168.1.105:8080/irserver/recog_image.json";
	public static String GRAPH_RECOGNIZE_URL = "http://124.232.150.75:80/irserver/recog_image.json";
	public static String SUGGESTION_URL = "http://124.232.150.75/irserver/yijian.do";
	public static String COOPERATION_URL = "http://124.232.150.75/irserver/hezuo.do";
	public static boolean isURLInited = false;

	public static final String ALI_SERVER_URL = "https://msp.alipay.com/x.htm";
	public static final String ALI_NOTIFY_URL = "https:///mobile.arvatopd.com";

	public static final String FILE_UPLOAD_URL = "https:///mobile.arvatopd.com";

	public static final String USER_INFO = "userinfo";// �����Ƿ��Զ���¼�������û������뵽
	public static final String ORDER_DATE = "selectDate";// ���ڿ�����ʾ������ʱ���

	public static final int Address_EDIT = 0;// ��ת���༭��ַҳ��
	public static final int Address_VIEW = 1;// ��ת���鿴����ҳ��

	public static final int NO_NEED_ACTIVE = 0;
	public static final int SMS_ACTIVE_WITHOUT_STORE = 1;
	public static final int SMS_ACTIVE_WITH_STORE = 2;
	public static final int STORE_CHECKIN_ACTIVE = 3;

	public static final String STORE_CHECKIN_ACTIVE_MODE = "=1";

	public static final double NO_TIME_RANGE = 0;
	public static final String NO_SMS_NUMBER = "";

	public static final int PRODUCT_LIST_PAGE_SIZE = 10;

	public static final int New_PRODUCT_LIST_TYPE = 1;
	public static final int HOT_PRODUCT_LIST_TYPE = 2;
	public static final int SELLER_LIST_HOT_SELLER_TYPE = 1;

	public static final int NO_INT_VALUE = -1;

	public static final int EXIST_ICON_TAG = -1;
	public static final int ICON_SCAN = 0;
	public static final int ICON_MYARVATO = 1;
	public static final int ICON_SHOPPINGCART = 2;
	public static final int ICON_ORDER = 3;
	public static final int ICON_SETUP = 4;

	public static final int QR_RESULT_CODE = 100;
	public static final int QR_RESULT_WEBVIEW = 101;

	public static final int RADIUS_1_KM = 1000;
	public static final int RADIUS_2_KM = 2000;
	public static final int RADIUS_5_KM = 5000;

	public static final int AREA_PROVINCE_WHAT = 1;
	public static final int AREA_CITY_WHAT = 2;
	public static final int AREA_DISTRICT_WHAT = 3;

	public static final int FAV_MODE_ALL = 0;
	public static final int FAV_MODE_PRODUCT = 1;
	public static final int FAV_MODE_COUPON = 2;
	public static final int FAV_MODE_STORE = 3;
	public static final int FAV_MODE_BOOK = 4;
	public static final int FAV_MODE_WEB_PRODUCT = 5;

	public static final String TYPE_PRODUCT = "product";
	public static final String TYPE_COUPON = "coupon";
	public static final String TYPE_STORE = "store";

	public static final int MODE_ERROR_DATA_BENA = 1;
	public static final int MODE_EMPTY_DATA_BENA = 2;

	// http://127.0.0.1/action?name=?
	public static final int SECONDARYURL_MODE_PARA = 1;
	// http://127.0.0.1/=?.json
	public static final int SECONDARYURL_MODE_FILENAME = 2;

	public static final int MENU_CURRENT_HOME = 0;
	public static final int MENU_CURRENT_SCAN_HISTORY = 1;
	public static final int MENU_CURRENT_FAVORITE = 2;

	public static final int VIEW_UNCLICKABLE = -1;

	public static final int MENU_COUPON = 6;
}
