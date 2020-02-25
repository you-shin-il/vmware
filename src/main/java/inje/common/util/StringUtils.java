package inje.common.util;

import inje.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * 문자 관련 유틸리티
 * @author  유기목
 * @since    2015. 9. 7.
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *      수정일          수정자                        수정내용
 *  ----------------     ----------      --------------------------------------------
 *   2015. 9. 7.       유기목        최초 생성
 *  </pre>
 */
public class StringUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(StringUtils.class);

    /**
     * 빈 문자열 <code>""</code>.
     */
    @SuppressWarnings("unused")
    private static final String EMPTY = "";

    /**
     * <p>Padding을 할 수 있는 최대 수치</p>
     */
    // private static final int PAD_LIMIT = 8192;
    /**
     * <p>An array of <code>String</code>s used for padding.</p>
     * <p>Used for efficient space padding. The length of each String expands as needed.</p>
     */
    /*
	private static final String[] PADDING = new String[Character.MAX_VALUE];

	static {
		// space padding is most common, start with 64 chars
		PADDING[32] = "                                                                ";
	}
     */

    /**
     * 문자열이 지정한 길이를 초과했을때 지정한길이에다가 해당 문자열을 붙여주는 메서드.
     * @param source 원본 문자열 배열
     * @param output 더할문자열
     * @param slength 지정길이
     * @return 지정길이로 잘라서 더할분자열 합친 문자열
     */
    public static String cutString(String source, String output, int slength) {
        String returnVal = null;
        if (source != null) {
            if (source.length() > slength) {
                returnVal = source.substring(0, slength) + output;
            } else
                returnVal = source;
        }
        return returnVal;
    }

    /**
     * 문자열이 지정한 길이를 초과했을때 해당 문자열을 삭제하는 메서드
     * @param source 원본 문자열 배열
     * @param slength 지정길이
     * @return 지정길이로 잘라서 더할분자열 합친 문자열
     */
    public static String cutString(String source, int slength) {
        String result = null;
        if (source != null) {
            if (source.length() > slength) {
                result = source.substring(0, slength);
            } else
                result = source;
        }
        return result;
    }

    /**
     * <p>
     * String이 비었거나("") 혹은 null 인지 검증한다.
     * </p>
     *
     * <pre>
     *  StringUtil.isEmpty(null)      = true
     *  StringUtil.isEmpty("")        = true
     *  StringUtil.isEmpty(" ")       = false
     *  StringUtil.isEmpty("bob")     = false
     *  StringUtil.isEmpty("  bob  ") = false
     * </pre>
     *
     * @param str - 체크 대상 스트링오브젝트이며 null을 허용함
     * @return <code>true</code> - 입력받은 String 이 빈 문자열 또는 null인 경우
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }


    /**
     * <p>기준 문자열에 포함된 모든 대상 문자(char)를 제거한다.</p>
     *
     * <pre>
     * StringUtil.remove(null, *)       = null
     * StringUtil.remove("", *)         = ""
     * StringUtil.remove("queued", 'u') = "qeed"
     * StringUtil.remove("queued", 'z') = "queued"
     * </pre>
     *
     * @param str  입력받는 기준 문자열
     * @param remove  입력받는 문자열에서 제거할 대상 문자열
     * @return 제거대상 문자열이 제거된 입력문자열. 입력문자열이 null인 경우 출력문자열은 null
     */
    public static String remove(String str, char remove) {
        if (isEmpty(str) || str.indexOf(remove) == -1) {
            return str;
        }
        char[] chars = str.toCharArray();
        int pos = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != remove) {
                chars[pos++] = chars[i];
            }
        }
        return new String(chars, 0, pos);
    }

    /**
     * <p>문자열 내부의 콤마 character(,)를 모두 제거한다.</p>
     *
     * <pre>
     * StringUtil.removeCommaChar(null)       = null
     * StringUtil.removeCommaChar("")         = ""
     * StringUtil.removeCommaChar("asdfg,qweqe") = "asdfgqweqe"
     * </pre>
     *
     * @param str 입력받는 기준 문자열
     * @return " , "가 제거된 입력문자열
     *  입력문자열이 null인 경우 출력문자열은 null
     */
    public static String removeCommaChar(String str) {
        return remove(str, ',');
    }

    /**
     * <p>문자열 내부의 마이너스 character(-)를 모두 제거한다.</p>
     *
     * <pre>
     * StringUtil.removeMinusChar(null)       = null
     * StringUtil.removeMinusChar("")         = ""
     * StringUtil.removeMinusChar("a-sdfg-qweqe") = "asdfgqweqe"
     * </pre>
     *
     * @param str  입력받는 기준 문자열
     * @return " - "가 제거된 입력문자열
     *  입력문자열이 null인 경우 출력문자열은 null
     */
    public static String removeMinusChar(String str) {
        return remove(str, '-');
    }


    /**
     * 원본 문자열의 포함된 특정 문자열을 새로운 문자열로 변환하는 메서드
     * @param source 원본 문자열
     * @param subject 원본 문자열에 포함된 특정 문자열
     * @param object 변환할 문자열
     * @return sb.toString() 새로운 문자열로 변환된 문자열
     */
    public static String replace(String source, String subject, String object) {
        StringBuffer rtnStr = new StringBuffer();
        String preStr = "";
        String nextStr = source;
        String srcStr  = source;

        while (srcStr.indexOf(subject) >= 0) {
            preStr = srcStr.substring(0, srcStr.indexOf(subject));
            nextStr = srcStr.substring(srcStr.indexOf(subject) + subject.length(), srcStr.length());
            srcStr = nextStr;
            rtnStr.append(preStr).append(object);
        }
        rtnStr.append(nextStr);
        return rtnStr.toString();
    }

    /**
     * 원본 문자열의 포함된 특정 문자열 첫번째 한개만 새로운 문자열로 변환하는 메서드
     * @param source 원본 문자열
     * @param subject 원본 문자열에 포함된 특정 문자열
     * @param object 변환할 문자열
     * @return sb.toString() 새로운 문자열로 변환된 문자열 / source 특정문자열이 없는 경우 원본 문자열
     */
    public static String replaceOnce(String source, String subject, String object) {
        StringBuffer rtnStr = new StringBuffer();
        String preStr = "";
        String nextStr = source;
        if (source.indexOf(subject) >= 0) {
            preStr = source.substring(0, source.indexOf(subject));
            nextStr = source.substring(source.indexOf(subject) + subject.length(), source.length());
            rtnStr.append(preStr).append(object).append(nextStr);
            return rtnStr.toString();
        } else {
            return source;
        }
    }

    /**
     * <code>subject</code>에 포함된 각각의 문자를 object로 변환한다.
     *
     * @param source 원본 문자열
     * @param subject 원본 문자열에 포함된 특정 문자열
     * @param object 변환할 문자열
     * @return sb.toString() 새로운 문자열로 변환된 문자열
     */
    public static String replaceChar(String source, String subject, String object) {
        StringBuffer rtnStr = new StringBuffer();
        String preStr = "";
        String nextStr = source;
        String srcStr  = source;

        char chA;

        for (int i = 0; i < subject.length(); i++) {
            chA = subject.charAt(i);

            if (srcStr.indexOf(chA) >= 0) {
                preStr = srcStr.substring(0, srcStr.indexOf(chA));
                nextStr = srcStr.substring(srcStr.indexOf(chA) + 1, srcStr.length());
                srcStr = rtnStr.append(preStr).append(object).append(nextStr).toString();
            }
        }

        return srcStr;
    }

    /**
     * <p><code>str</code> 중 <code>searchStr</code>의 시작(index) 위치를 반환.</p>
     *
     * <p>입력값 중 <code>null</code>이 있을 경우 <code>-1</code>을 반환.</p>
     *
     * <pre>
     * StringUtil.indexOf(null, *)          = -1
     * StringUtil.indexOf(*, null)          = -1
     * StringUtil.indexOf("", "")           = 0
     * StringUtil.indexOf("aabaabaa", "a")  = 0
     * StringUtil.indexOf("aabaabaa", "b")  = 2
     * StringUtil.indexOf("aabaabaa", "ab") = 1
     * StringUtil.indexOf("aabaabaa", "")   = 0
     * </pre>
     *
     * @param str  검색 문자열
     * @param searchStr  검색 대상문자열
     * @return 검색 문자열 중 검색 대상문자열이 있는 시작 위치 검색대상 문자열이 없거나 null인 경우 -1
     */
    public static int indexOf(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return -1;
        }
        return str.indexOf(searchStr);
    }


    /**
     * <p>오라클의 decode 함수와 동일한 기능을 가진 메서드이다.
     * <code>sourStr</code>과 <code>compareStr</code>의 값이 같으면
     * <code>returStr</code>을 반환하며, 다르면  <code>defaultStr</code>을 반환한다.
     * </p>
     *
     * <pre>
     * StringUtil.decode(null, null, "foo", "bar")= "foo"
     * StringUtil.decode("", null, "foo", "bar") = "bar"
     * StringUtil.decode(null, "", "foo", "bar") = "bar"
     * StringUtil.decode("하이", "하이", null, "bar") = null
     * StringUtil.decode("하이", "하이  ", "foo", null) = null
     * StringUtil.decode("하이", "하이", "foo", "bar") = "foo"
     * StringUtil.decode("하이", "하이  ", "foo", "bar") = "bar"
     * </pre>
     *
     * @param sourceStr 비교할 문자열
     * @param compareStr 비교 대상 문자열
     * @param returnStr sourceStr와 compareStr의 값이 같을 때 반환할 문자열
     * @param defaultStr sourceStr와 compareStr의 값이 다를 때 반환할 문자열
     * @return sourceStr과 compareStr의 값이 동일(equal)할 때 returnStr을 반환하며,
     *         <br/>다르면 defaultStr을 반환한다.
     */
    public static String decode(String sourceStr, String compareStr, String returnStr, String defaultStr) {
        if (sourceStr == null && compareStr == null) {
            return returnStr;
        }

        if (sourceStr == null && compareStr != null) {
            return defaultStr;
        }

        if (sourceStr.trim().equals(compareStr)) {
            return returnStr;
        }

        return defaultStr;
    }

    /**
     * <p>오라클의 decode 함수와 동일한 기능을 가진 메서드이다.
     * <code>sourStr</code>과 <code>compareStr</code>의 값이 같으면
     * <code>returStr</code>을 반환하며, 다르면  <code>sourceStr</code>을 반환한다.
     * </p>
     *
     * <pre>
     * StringUtil.decode(null, null, "foo") = "foo"
     * StringUtil.decode("", null, "foo") = ""
     * StringUtil.decode(null, "", "foo") = null
     * StringUtil.decode("하이", "하이", "foo") = "foo"
     * StringUtil.decode("하이", "하이 ", "foo") = "하이"
     * StringUtil.decode("하이", "바이", "foo") = "하이"
     * </pre>
     *
     * @param sourceStr 비교할 문자열
     * @param compareStr 비교 대상 문자열
     * @param returnStr sourceStr와 compareStr의 값이 같을 때 반환할 문자열
     * @return sourceStr과 compareStr의 값이 동일(equal)할 때 returnStr을 반환하며,
     *         <br/>다르면 sourceStr을 반환한다.
     */
    public static String decode(String sourceStr, String compareStr, String returnStr) {
        return decode(sourceStr, compareStr, returnStr, sourceStr);
    }

    /**
     * 객체가 null인지 확인하고 null인 경우 "" 로 바꾸는 메서드
     * @param object 원본 객체
     * @return resultVal 문자열
     */
    public static String isNullToString(Object object) {
        String string = "";

        if (object != null) {
            string = object.toString().trim();
        }

        return string;
    }

    /**
     *<pre>
     * 인자로 받은 String이 null일 경우 &quot;&quot;로 리턴한다.
     * &#064;param src null값일 가능성이 있는 String 값.
     * &#064;return 만약 String이 null 값일 경우 &quot;&quot;로 바꾼 String 값.
     *</pre>
     */
    public static String nullConvert(Object src) {
    if (src != null) {
    	if (src instanceof BigDecimal) {
    	    return ((BigDecimal)src).toString();
    	}
    	if (src instanceof Integer) {
    	    return ((Integer)src).toString();
    	}
    }

	if (src == null || src.equals("null")) {
	    return "";
	} else {
	    return ((String)src).trim();
	}
    }

    /**
     *<pre>
     * 인자로 받은 String이 null일 경우 &quot;&quot;로 리턴한다.
     * &#064;param src null값일 가능성이 있는 String 값.
     * &#064;return 만약 String이 null 값일 경우 &quot;&quot;로 바꾼 String 값.
     *</pre>
     */
    public static String nullConvert(String src) {

	if (src == null || src.equals("null") || StringUtils.isEmpty(src) || " ".equals(src)) {
	    return "";
	} else {
	    return src.trim();
	}
    }
    
    /**
     *<pre>
     * 인자로 받은 String이 null일 경우 nullStr로 리턴한다.
     * param src null값일 가능성이 있는 String 값.
     * param nullStr src값이 null일 경우 리턴값.
     *</pre>
     */
    public static String nullConvert(String src, String nullStr) {
    	
    	if (src == null || src.equals("null") || StringUtils.isEmpty(src) || " ".equals(src)) {
    		return nullStr;
    	} else {
    		return src.trim();
    	}
    }

    /**
     *<pre>
     * 인자로 받은 String이 null일 경우 &quot;0&quot;로 리턴한다.
     * &#064;param src null값일 가능성이 있는 String 값.
     * &#064;return 만약 String이 null 값일 경우 &quot;0&quot;로 바꾼 String 값.
     *</pre>
     */
    public static int zeroConvert(Object src) {

	if (src == null || src.equals("null")) {
	    return 0;
	} else {
	    return Integer.parseInt(((String)src).trim());
	}
    }

    /**
     *<pre>
     * 인자로 받은 String이 null일 경우 &quot;&quot;로 리턴한다.
     * &#064;param src null값일 가능성이 있는 String 값.
     * &#064;return 만약 String이 null 값일 경우 &quot;&quot;로 바꾼 String 값.
     *</pre>
     */
    public static int zeroConvert(String src) {

	if (src == null || src.equals("null") || StringUtils.isEmpty(src) || " ".equals(src)) {
	    return 0;
	} else {
	    return Integer.parseInt(src.trim());
	}
    }

    /**
     * <p>문자열에서 {@link Character#isWhitespace(char)}에 정의된
     * 모든 공백문자를 제거한다.</p>
     *
     * <pre>
     * StringUtil.removeWhitespace(null)         = null
     * StringUtil.removeWhitespace("")           = ""
     * StringUtil.removeWhitespace("abc")        = "abc"
     * StringUtil.removeWhitespace("   ab  c  ") = "abc"
     * </pre>
     *
     * @param str  공백문자가 제거도어야 할 문자열
     * @return the 공백문자가 제거된 문자열, null이 입력되면 <code>null</code>이 리턴
     */
    public static String removeWhitespace(String str) {
        if (isEmpty(str)) {
            return str;
        }
        int sz = str.length();
        char[] chs = new char[sz];
        int count = 0;
        for (int i = 0; i < sz; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                chs[count++] = str.charAt(i);
            }
        }
        if (count == sz) {
            return str;
        }

        return new String(chs, 0, count);
    }

    /**
     * Html 코드가 들어간 문서를 표시할때 태그에 손상없이 보이기 위한 메서드
     *
     * @param strString
     * @return HTML 태그를 치환한 문자열
     */
	public static String checkHtmlView(String strString) {
		String strNew = "";

		StringBuffer strTxt = new StringBuffer("");

		char chrBuff;
		int len = strString.length();

		for (int i = 0; i < len; i++) {
			chrBuff = (char) strString.charAt(i);

			switch (chrBuff) {
				case '<':
					strTxt.append("&lt;");
					break;
				case '>':
					strTxt.append("&gt;");
					break;
				case '"':
					strTxt.append("&quot;");
					break;
				case 10:
					strTxt.append("<br>");
					break;
				case ' ':
					strTxt.append("&nbsp;");
					break;
				//case '&' :
				//strTxt.append("&amp;");
				//break;
				default:
					strTxt.append(chrBuff);
			}
		}

		strNew = strTxt.toString();

		return strNew;
	}


    /**
     * 문자열을 지정한 분리자에 의해 배열로 리턴하는 메서드.
     * @param source 원본 문자열
     * @param separator 분리자
     * @return result 분리자로 나뉘어진 문자열 배열
     *//*
    public static String[] split(String source, String separator) throws NullPointerException {
        String[] returnVal = null;
        int cnt = 1;

        int index = source.indexOf(separator);
        int index0 = 0;
        while (index >= 0) {
            cnt++;
            index = source.indexOf(separator, index + 1);
        }
        returnVal = new String[cnt];
        cnt = 0;
        index = source.indexOf(separator);
        while (index >= 0) {
            returnVal[cnt] = source.substring(index0, index);
            index0 = index + 1;
            index = source.indexOf(separator, index + 1);
            cnt++;
        }
        returnVal[cnt] = source.substring(index0);

        return returnVal;
    }*/

    /**
     * <p>{@link String#toLowerCase()}를 이용하여 소문자로 변환한다.</p>
     *
     * <pre>
     * StringUtil.lowerCase(null)  = null
     * StringUtil.lowerCase("")    = ""
     * StringUtil.lowerCase("aBc") = "abc"
     * </pre>
     *
     * @param str 소문자로 변환되어야 할 문자열
     * @return 소문자로 변환된 문자열, null이 입력되면 <code>null</code> 리턴
     */
    public static String lowerCase(String str) {
        if (str == null) {
            return null;
        }

        return str.toLowerCase();
    }

    /**
     * <p>{@link String#toUpperCase()}를 이용하여 대문자로 변환한다.</p>
     *
     * <pre>
     * StringUtil.upperCase(null)  = null
     * StringUtil.upperCase("")    = ""
     * StringUtil.upperCase("aBc") = "ABC"
     * </pre>
     *
     * @param str 대문자로 변환되어야 할 문자열
     * @return 대문자로 변환된 문자열, null이 입력되면 <code>null</code> 리턴
     */
    public static String upperCase(String str) {
        if (str == null) {
            return null;
        }

        return str.toUpperCase();
    }

    /**
     * <p>입력된 String의 앞쪽에서 두번째 인자로 전달된 문자(stripChars)를 모두 제거한다.</p>
     *
     * <pre>
     * StringUtil.stripStart(null, *)          = null
     * StringUtil.stripStart("", *)            = ""
     * StringUtil.stripStart("abc", "")        = "abc"
     * StringUtil.stripStart("abc", null)      = "abc"
     * StringUtil.stripStart("  abc", null)    = "abc"
     * StringUtil.stripStart("abc  ", null)    = "abc  "
     * StringUtil.stripStart(" abc ", null)    = "abc "
     * StringUtil.stripStart("yxabc  ", "xyz") = "abc  "
     * </pre>
     *
     * @param str 지정된 문자가 제거되어야 할 문자열
     * @param stripChars 제거대상 문자열
     * @return 지정된 문자가 제거된 문자열, null이 입력되면 <code>null</code> 리턴
     */
    public static String stripStart(String str, String stripChars) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        int start = 0;
        if (stripChars == null) {
            while ((start != strLen) && Character.isWhitespace(str.charAt(start))) {
                start++;
            }
        } else if (stripChars.length() == 0) {
            return str;
        } else {
            while ((start != strLen) && (stripChars.indexOf(str.charAt(start)) != -1)) {
                start++;
            }
        }

        return str.substring(start);
    }


    /**
     * <p>입력된 String의 뒤쪽에서 두번째 인자로 전달된 문자(stripChars)를 모두 제거한다.</p>
     *
     * <pre>
     * StringUtil.stripEnd(null, *)          = null
     * StringUtil.stripEnd("", *)            = ""
     * StringUtil.stripEnd("abc", "")        = "abc"
     * StringUtil.stripEnd("abc", null)      = "abc"
     * StringUtil.stripEnd("  abc", null)    = "  abc"
     * StringUtil.stripEnd("abc  ", null)    = "abc"
     * StringUtil.stripEnd(" abc ", null)    = " abc"
     * StringUtil.stripEnd("  abcyx", "xyz") = "  abc"
     * </pre>
     *
     * @param str 지정된 문자가 제거되어야 할 문자열
     * @param stripChars 제거대상 문자열
     * @return 지정된 문자가 제거된 문자열, null이 입력되면 <code>null</code> 리턴
     */
    public static String stripEnd(String str, String stripChars) {
        int end;
        if (str == null || (end = str.length()) == 0) {
            return str;
        }

        if (stripChars == null) {
            while ((end != 0) && Character.isWhitespace(str.charAt(end - 1))) {
                end--;
            }
        } else if (stripChars.length() == 0) {
            return str;
        } else {
            while ((end != 0) && (stripChars.indexOf(str.charAt(end - 1)) != -1)) {
                end--;
            }
        }

        return str.substring(0, end);
    }

    /**
     * <p>입력된 String의 앞, 뒤에서 두번째 인자로 전달된 문자(stripChars)를 모두 제거한다.</p>
     *
     * <pre>
     * StringUtil.strip(null, *)          = null
     * StringUtil.strip("", *)            = ""
     * StringUtil.strip("abc", null)      = "abc"
     * StringUtil.strip("  abc", null)    = "abc"
     * StringUtil.strip("abc  ", null)    = "abc"
     * StringUtil.strip(" abc ", null)    = "abc"
     * StringUtil.strip("  abcyx", "xyz") = "  abc"
     * </pre>
     *
     * @param str 지정된 문자가 제거되어야 할 문자열
     * @param stripChars 제거대상 문자열
     * @return 지정된 문자가 제거된 문자열, null이 입력되면 <code>null</code> 리턴
     */
    public static String strip(String str, String stripChars) {
	if (isEmpty(str)) {
	    return str;
	}

	String srcStr = str;
	srcStr = stripStart(srcStr, stripChars);

	return stripEnd(srcStr, stripChars);
    }

    /**
     * 문자열을 지정한 분리자에 의해 지정된 길이의 배열로 리턴하는 메서드.
     * @param source 원본 문자열
     * @param separator 분리자
     * @param arraylength 배열 길이
     * @return 분리자로 나뉘어진 문자열 배열
     *//*
    public static String[] split(String source, String separator, int arraylength) throws NullPointerException {
        String[] returnVal = new String[arraylength];
        int cnt = 0;
        int index0 = 0;
        int index = source.indexOf(separator);
        while (index >= 0 && cnt < (arraylength - 1)) {
            returnVal[cnt] = source.substring(index0, index);
            index0 = index + 1;
            index = source.indexOf(separator, index + 1);
            cnt++;
        }
        returnVal[cnt] = source.substring(index0);
        if (cnt < (arraylength - 1)) {
            for (int i = cnt + 1; i < arraylength; i++) {
                returnVal[i] = "";
            }
        }

        return returnVal;
    }*/

    /**
     * 문자열을 다양한 문자셋(EUC-KR[KSC5601],UTF-8..)을 사용하여 인코딩하는 기능 역으로 디코딩하여 원래의 문자열을
     * 복원하는 기능을 제공함 String temp = new String(문자열.getBytes("바꾸기전 인코딩"),"바꿀 인코딩");
     * String temp = new String(문자열.getBytes("8859_1"),"KSC5601"); => UTF-8 에서
     * EUC-KR
     *
     * @param srcString
     *            - 문자열
     * @param srcCharsetNm
     *            - 원래 CharsetNm
     * @param
     *            - CharsetNm
     * @return 인(디)코딩 문자열
     * @exception
     * @see
     */
    public static String getEncdDcd(String srcString, String srcCharsetNm, String cnvrCharsetNm) {

	String rtnStr = null;

	if (srcString == null)
	    return null;

	try {
	    rtnStr = new String(srcString.getBytes(srcCharsetNm), cnvrCharsetNm);
	} catch (UnsupportedEncodingException e) {
	    rtnStr = null;
	}

	return rtnStr;
    }

/**
     * 특수문자를 웹 브라우저에서 정상적으로 보이기 위해 특수문자를 처리('<' -> & lT)하는 기능이다
     * @param 	srcString 		- '<'
     * @return 	변환문자열('<' -> "&lt"
     * @exception
     * @see
     */
    public static String getSpclStrCnvr(String srcString) {

	String rtnStr = null;

	try {
	    StringBuffer strTxt = new StringBuffer("");

	    char chrBuff;
	    int len = srcString.length();

	    for (int i = 0; i < len; i++) {
		chrBuff = (char)srcString.charAt(i);

		switch (chrBuff) {
		case '<':
		    strTxt.append("&lt;");
		    break;
		case '>':
		    strTxt.append("&gt;");
		    break;
		case '&':
		    strTxt.append("&amp;");
		    break;
		default:
		    strTxt.append(chrBuff);
		}
	    }

	    rtnStr = strTxt.toString();

	} catch(NullPointerException ne){
        LOGGER.info("NullPointerException 에러가 발생했습니다.");
    } catch (Exception e) {
		LOGGER.debug("{}", e);
	}

	return rtnStr;
    }

    /**
     * 응용어플리케이션에서 고유값을 사용하기 위해 시스템에서17자리의TIMESTAMP값을 구하는 기능
     *
     * @param
     * @return Timestamp 값
     * @exception
     * @see
     */
    public static String getTimeStamp() {

	String rtnStr = null;

	// 문자열로 변환하기 위한 패턴 설정(년도-월-일 시:분:초:초(자정이후 초))
	String pattern = "yyyyMMddhhmmssSSS";

	SimpleDateFormat sdfCurrent = new SimpleDateFormat(pattern, Locale.KOREA);
    Timestamp ts = new Timestamp(System.currentTimeMillis());

    rtnStr = sdfCurrent.format(ts.getTime());

	return rtnStr;
    }

    /**
     * html의 특수문자를 표현하기 위해
     *
     * @param srcString
     * @return String
     * @exception Exception
     * @see
     */
    public static String getHtmlStrCnvr(String srcString) {

    	String tmpString = srcString;

		tmpString = tmpString.replaceAll("&lt;", "<");
		tmpString = tmpString.replaceAll("&gt;", ">");
		tmpString = tmpString.replaceAll("&amp;", "&");
		tmpString = tmpString.replaceAll("&nbsp;", " ");
		tmpString = tmpString.replaceAll("&apos;", "\'");
		tmpString = tmpString.replaceAll("&quot;", "\"");

		return  tmpString;

	}

    /**
     * <p>날짜 형식의 문자열 내부에 마이너스 character(-)를 추가한다.</p>
     *
     * <pre>
     *   StringUtil.addMinusChar("20100901") = "2010-09-01"
     * </pre>
     *
     * @param date  입력받는 문자열
     * @return " - "가 추가된 입력문자열
     */
	public static String addMinusChar(String date) {
		if(date.length() == 8)
		    return date.substring(0,4).concat("-").concat(date.substring(4,6)).concat("-").concat(date.substring(6,8));
		else return "";
	}

    /**
     * null String을 ""(공백) String으로 변환한다.
     *
     * @param text
     * @return String
     */
    public static String nvl(Object text) {
        String rtn;

        rtn = "";
        if (text == null) {
            return rtn;
        }
        else if (String.valueOf(text).toLowerCase().trim().equals("null")) {
            return rtn;
        }
        else {
            return String.valueOf(text);
        }
    }

    /**
     * null String을 str값으로 String으로 변환한다.
     * @param text
     * @param str
     * @return
     */
    public static String nvl(Object text, String str) {
        String rtn;

        rtn = str;
        if (text == null) {
            return rtn;
        }
        else if (String.valueOf(text).toLowerCase().equals("null")) {
            return rtn;
        }
        else {
            if (String.valueOf(text).equals("")) {
                return rtn;
            }
            return String.valueOf(text);
        }
    }

    /**
     * null String을 ""(공백) String으로 변환한다.
     *
     * @param text
     * @return String
     */
    public static String nvl(String text) {
        String rtn;

        rtn = "";
        if (text == null) {
            return rtn;
        }
        else if (text.toLowerCase().equals("null")) {
            return rtn;
        }
        else {
            return text;
        }
    }

    /**
     * null String을 ""(공백) String으로 변환한다. isNull이 false이면 "null"이란 String은 ""으로 변환하지 않는다.
     *
     * @param text
     * @param isNull
     * @return String
     */
    public static String nvl(String text, boolean isNull) {
        String rtn;

        rtn = "";
        if (text == null) {
            return rtn;
        }
        else if (isNull && text.toLowerCase().equals("null")) {
            return rtn;
        }
        else {
            return text;
        }
    }

    /**
     * null String 또는 공백이나 "null"을 str String으로 변환한다.
     *
     * @param text
     * @param str
     * @return String
     */
    public static String nvl(String text, String str) {
        String rtn = "";

        if (text == null) {
            rtn = str;
        }
        else if (StringUtils.isEmpty(text)) {
            rtn = str;
        }
        else if ("null".equals(text.toLowerCase())) {
            rtn = str;
        }
        else {
            rtn = text;

        }
        return rtn;
    }

    /**
     * 파일 확장자 리턴
     * @param fileName
     * @return
     */
    public static String getFileExt(String fileName) {
        String ext = "";

        int index = fileName.lastIndexOf(".");
        if (index != -1) {
            ext  = fileName.substring(index + 1);
        }
        return ext;
    }

    /**
     * 문자열 , 구분자로 문자리스트 반환
     * @param input
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static List getList(String input) {
        List result = new ArrayList();
        String[] arrInput = input.split(",");

        for (int i = 0; i < arrInput.length; ++i) {
          result.add(arrInput[i].trim());
        }

        return result;
    }

    /**
     * 패스워드 decode시 이용하는 스크립트
     * @param pw
     * @return
     */
    public static void checkPassword(String pw) throws BusinessException {
        byte b[] = pw.getBytes();
        int sc[] = {0, 0, 0};

        // 같은문자 반복 또는 연속되는 문자(abcd, 1234, 8765 등등..) 체크
        for(int i = 0; i < b.length; i++){
            int temp = b[i];
            int temp2 = i > 0 ? b[i-1] : 0;

            if(i > 0 && temp == temp2) {
                sc[0]++;
            } else {
                sc[0] = 0;
            }
            if(i > 0 && temp == temp2+1) {
                sc[1]++;
            } else {
                sc[1] = 0;
            }
            if(i > 0 && temp == temp2-1) {
                sc[2]++;
            } else {
                sc[2] = 0;
            }

            if(sc[0] >= 3 || sc[1] >= 3 || sc[2] >= 3){
                throw new BusinessException("같은문자가 반복되거나 연속되는 문자가 중복됩니다.(4회이상연속)");
            }
        }

        //키보드 연속 배열 체크
        for(int i = 0; i < pw.length()-3; i++){
            String temp = pw.substring(i, i+4);
            if("qwertyuiop".indexOf(temp) >= 0){
                throw new BusinessException("키보드상의 배열상 연속되는 문자는 사용하실 수 없습니다.(4회이상연속)");
            }
            if("asdfghjkl".indexOf(temp) >= 0){
                throw new BusinessException("키보드상의 배열상 연속되는 문자는 사용하실 수 없습니다.(4회이상연속)");
            }
            if("zxcvbnm".indexOf(temp) >= 0){
                throw new BusinessException("키보드상의 배열상 연속되는 문자는 사용하실 수 없습니다.(4회이상연속)");
            }
            if("poiuytrewq".indexOf(temp) >= 0){
                throw new BusinessException("키보드상의 배열상 연속되는 문자는 사용하실 수 없습니다.(4회이상연속)");
            }
            if("lkjhgfdsa".indexOf(temp) >= 0){
                throw new BusinessException("키보드상의 배열상 연속되는 문자는 사용하실 수 없습니다.(4회이상연속)");
            }
            if("mnbvcxz".indexOf(temp) >= 0){
                throw new BusinessException("키보드상의 배열상 연속되는 문자는 사용하실 수 없습니다.(4회이상연속)");
            }
        }

        //연속되는 6개의 숫자가 년/월/일 의 형식에 맞을경우 확인
        for(int i = 0; i < pw.length()-5; i++) {
            String temp1 = pw.substring(i, i+6);
            byte b1[] = temp1.getBytes();
            boolean isnum = true;
            for(int j = 0; j < b1.length; j++){
                if(b1[j] < '0' || b1[j] > '9') isnum = false;
            }

            if(isnum){
                int mm = Integer.parseInt(temp1.substring(2, 4));
                int dd = Integer.parseInt(temp1.substring(4, 6));

                if(mm == 1 || mm == 3 || mm == 5 || mm == 7 || mm == 8 || mm == 10 || mm == 12) {
                    if(dd <= 31){
                        throw new BusinessException("날짜형식으로 확인되는 문자는 사용하실 수 없습니다.");
                    }
                }
                if(mm == 4 || mm == 6 || mm == 9 || mm == 11) {
                    if(dd <= 30){
                        throw new BusinessException("날짜형식으로 확인되는 문자는 사용하실 수 없습니다.");
                    }
                }
                if(mm == 2) {
                    if(dd <= 28){
                        throw new BusinessException("날짜형식으로 확인되는 문자는 사용하실 수 없습니다.");
                    }
                }
            }
        }
    }

    /**
     * 문장에 포함된 숫자 아스터리스크로 변환
     * @param str
     * @return
     */
    public static String numberConvAsterisk(String str) {
        return str.replaceAll("[0-9]", "*");
    }

    /**
     * 크로스사이트 스크립트에 대해 일반 태그 제외한 악용 가능한 태그에 대해 정규식으로 제거
     * @param str
     * @return
     */
    public static String xssPrevention(String str) {
        if(str != null) {
            str = str.replaceAll("(?i)[\\s]*on(.*)[\\s]*=(.*)[(](.*)[)][;]?[\\s]*[\"|\'][\\s]", " ");
            str = str.replaceAll("(?i)[\\s]*type[\\s]*=[\"|\']?[\\s]*text[\\s]*/[\\s]*javascript[\\s]*[\"|\']?", "");
            str = str.replaceAll("(?i)[\\s]*eval[(](.*)[)](;)?[\\s]", "");
            str = str.replaceAll("(?i)[\\s]*[:]?expression[(](.*)[)]", "");
            str = str.replaceAll("(?i)[\"|\'][\\s]*javascript[\\s]*:(.*)(;)?[\"|\']", "").replaceAll("(?i)[\\s]*javascript[\\s]*:(.*)[(](.*)[)](;)?", "");
            str = str.replaceAll("(?i)[\"|\'][\\s]*vbscript[\\s]*:(.*)(;)?[\"|\']", "").replaceAll("(?i)[\\s]*vbscript[\\s]*:(.*)[(](.*)[)](;)?", "");

            str = str.replaceAll("(?i)<[\\s]*(no)?script[\\s]*[^>]*>[\\r\\n]*?[^<]*<[\\s]*\\/[\\s]*(no)?script[\\s]*>", "").replaceAll("(?i)<[\\s]*(no)?script(.*)(\\/)?[\\s]*>", "");
            str = str.replaceAll("(?i)<[\\s]*xss[\\s]*[^>]*>[\\r\\n]*?[^<]*<[\\s]*\\/[\\s]*xss[\\s]*>", "").replaceAll("(?i)<[\\s]*xss(.*)(\\/)?[\\s]*>", "");
            str = str.replaceAll("(?i)<[\\s]*iframe[\\s]*[^>]*>[\\r\\n]*?[^<]*<[\\s]*\\/[\\s]*iframe[\\s]*>", "").replaceAll("(?i)<[\\s]*iframe(.*)(\\/)?[\\s]*>", "");
            str = str.replaceAll("(?i)<[\\s]*document[\\s]*[^>]*>[\\r\\n]*?[^<]*<[\\s]*\\/[\\s]*document[\\s]*>", "").replaceAll("(?i)<[\\s]*document(.*)(\\/)?[\\s]*>", "");
            str = str.replaceAll("(?i)<[\\s]*applet[\\s]*[^>]*>[\\r\\n]*?[^<]*<[\\s]*\\/[\\s]*applet[\\s]*>", "").replaceAll("(?i)<[\\s]*applet(.*)(\\/)?[\\s]*>", "");
            str = str.replaceAll("(?i)<[\\s]*embed[\\s]*[^>]*>[\\r\\n]*?[^<]*<[\\s]*\\/[\\s]*embed[\\s]*>", "").replaceAll("(?i)<[\\s]*embed(.*)(\\/)?[\\s]*>", "");
            str = str.replaceAll("(?i)<[\\s]*object[\\s]*[^>]*>[\\r\\n]*?[^<]*<[\\s]*\\/[\\s]*object[\\s]*>", "").replaceAll("(?i)<[\\s]*object(.*)(\\/)?[\\s]*>", "");
            str = str.replaceAll("(?i)<[\\s]*frame[\\s]*[^>]*>[\\r\\n]*?[^<]*<[\\s]*\\/[\\s]*frame[\\s]*>", "").replaceAll("(?i)<[\\s]*frame(.*)(\\/)?[\\s]*>", "");
            str = str.replaceAll("(?i)<[\\s]*frameset[\\s]*[^>]*>[\\r\\n]*?[^<]*<[\\s]*\\/[\\s]*frameset[\\s]*>", "").replaceAll("(?i)<[\\s]*frameset(.*)(\\/)?[\\s]*>", "");
            str = str.replaceAll("(?i)<[\\s]*bgsound[\\s]*[^>]*>[\\r\\n]*?[^<]*<[\\s]*\\/[\\s]*bgsound[\\s]*>", "").replaceAll("(?i)<[\\s]*bgsound(.*)(\\/)?[\\s]*>", "");
            str = str.replaceAll("(?i)<[\\s]*link[\\s]*[^>]*>[\\r\\n]*?[^<]*<[\\s]*\\/[\\s]*link[\\s]*>", "").replaceAll("(?i)<[\\s]*link(.*)(\\/)?[\\s]*>", "");
            str = str.replaceAll("(?i)<[\\s]*meta[\\s]*[^>]*>[\\r\\n]*?[^<]*<[\\s]*\\/[\\s]*meta[\\s]*>", "").replaceAll("(?i)<[\\s]*meta(.*)(\\/)?[\\s]*>", "");
        }

        return str;
    }

    /**
     * xss 문자열 변환
     */
    public static String xssConv(String strString) {
        String strNew = "";

        StringBuffer strTxt = new StringBuffer("");

        char chrBuff;
        int len = strString.length();

        for (int i = 0; i < len; i++) {
            chrBuff = (char) strString.charAt(i);
            switch (chrBuff) {
                case '<':
                    strTxt.append("&lt;");
                    break;
                case '>':
                    strTxt.append("&gt;");
                    break;
                case '"':
                    strTxt.append("&quot;");
                    break;
                case '\\':
                    strTxt.append("&gt;");
                    break;
                case '&':
                    strTxt.append("&amp;");
                    break;
                case '\'':
                    strTxt.append("&#x27;");
                    break;
                case '`':
                    strTxt.append("&#x60;");
                    break;
                //case '&' :
                //strTxt.append("&amp;");
                //break;
                default:
                    strTxt.append(chrBuff);
            }
        }

        strNew = strTxt.toString();

        return strNew;
    }

    /**
     * 한글만 인코딩 변환
     * @throws UnsupportedEncodingException 
     */
	public static String koreanChangeUtf8(String targetStr) throws UnsupportedEncodingException {
		String str = URLEncoder.encode(targetStr, "utf-8");
		return str = str.replace("%2F", "/").replaceAll("\\+", "%20");// 공백 및 / 문자 변환
/*		char[] targetCharArr = targetStr.toCharArray();

		for(char targetChar : targetCharArr) {
	        if (targetChar >= '\uAC00' && targetChar <= '\uD7A3') {
	            String targetText = String.valueOf(targetChar);
	            try {
	            	targetStr = targetStr.replace(targetText, URLEncoder.encode(targetText, "utf-8"));
	            } catch (UnsupportedEncodingException e) {
	                e.printStackTrace();
	            }
	        } 
		}

		return targetStr;*/
	}

    /**
     * 한글만 인코딩 변환
     * @throws UnsupportedEncodingException 
     */
    public static String koreanChangeUnicode(String targetStr) {
        StringBuffer str = new StringBuffer();

        for (int i = 0; i < targetStr.length(); i++) {
            if(targetStr.charAt(i) >= '\uAC00' && targetStr.charAt(i) <= '\uD7A3') {
                str.append("\\u");
                str.append(Integer.toHexString((int) targetStr.charAt(i)));
            }else {
            	str.append(targetStr.charAt(i));
            }

        }
        return str.toString();
    }

    /**
     * GB KB로 변환
     */
    public static long gbToKb(long gb) {
        return gb * 1048576;
    }

    /**
     * GB BYTE로 변환
     */
    public static long gbToByte(long gb) {
        return gb * 1073741824;
    }

    /**
     * KB GB로 변환
     */
    public static long kbToGb(long gb) {
        return gb / 1048576;
    }

    /**
	 * 용량 단위 변환
	 * @param input 데이터 용량
	 * @param cur_type 현재 데이터 타입
	 * @param change_type 변환할 데이터 타입
	 * @param dp 소수점 자리수(단위가 커질경우만사용)
	 * @return
	 */
	public static double changeByteType(int input, String cur_type, String change_type, int dp){
		String bType = "BKMGT" ;
		int start = bType.indexOf(cur_type);
		int end = bType.indexOf(change_type);
		double result = input;
		if (start <= end) {
			int point = 10 ;
			int dp_point = 1 ;
			for (int j = 0 ; j < dp; j++) {
				dp_point *= point;
			}
			for (int i = start; i < end; i++) {
				Double inputDoubleByte = (double) result;
				result = Math.round(inputDoubleByte/1024 * dp_point) / dp_point;
			}
		} else {
			for (int i = start; i > end; i--) {
				Double inputDoubleByte = (double) result;
				result = Math.round(inputDoubleByte*1024);
			}
		}
		return result;
	}

    /**
     * 맨 앞글자만 대분자로 변환
     */
    public static String firstCharUpper(String targetStr) {
        return Character.toUpperCase(targetStr.charAt(0)) + targetStr.substring(1);
    }
    
    /**
	 * 핸드폰 번호 포맷
	 */
	public static String mobileFormat(String mobileNumber) {
		if (mobileNumber == null || "".equals(mobileNumber)) {
			return null;
		} else {
			String regEx = "(\\d{3})(\\d{3,4})(\\d{4})";
		   if(!Pattern.matches(regEx, mobileNumber)) return null;
		   return mobileNumber.replaceAll(regEx, "$1-$2-$3");
		}
	}
	/**
	 * 전화번호 번호 포맷
	 */
	public static String telFormat(String telNumber) {
		if (telNumber == null) {
			return "";
		}
		if (telNumber.length() == 8) {
			return telNumber.replaceFirst("^([0-9]{4})([0-9]{4})$", "$1-$2");
		} else if (telNumber.length() == 12) {
			return telNumber.replaceFirst("(^[0-9]{4})([0-9]{4})([0-9]{4})$", "$1-$2-$3");
		}
		return telNumber.replaceFirst("(^02|[0-9]{3})([0-9]{3,4})([0-9]{4})$", "$1-$2-$3");

	}
	
	
}