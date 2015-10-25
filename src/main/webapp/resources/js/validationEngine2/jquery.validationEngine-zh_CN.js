(function ($) {
    $.fn.validationEngineLanguage = function () {
    };
    $.validationEngineLanguage = {
        newLang: function () {
            $.validationEngineLanguage.allRules = {
                "required": {
                    "regex": "none",
                    "alertText": "* 此处不可空白",
                    "alertTextCheckboxMultiple": "* 请选择一个项目",
                    "alertTextCheckboxe": "* 您必须勾选此栏",
                    "alertTextDateRange": "* 日期范围不可空白"
                },
                "requiredInFunction": {
                    "func": function (field, rules, i, options) {
                        return (field.val() == "test") ? true : false;
                    }, "alertText": "* Field must equal test"
                },
                "dateRange": {"regex": "none", "alertText": "* 无效的 ", "alertText2": " 日期范围"},
                "dateTimeRange": {"regex": "none", "alertText": "* 无效的 ", "alertText2": " 时间范围"},
                "minSize": {"regex": "none", "alertText": "* 最少 ", "alertText2": " 个字符"},
                "maxSize": {"regex": "none", "alertText": "* 最多 ", "alertText2": " 个字符"},
                "groupRequired": {"regex": "none", "alertText": "* 你必需选填其中一个栏位"},
                "min": {"regex": "none", "alertText": "* 最小值為 "},
                "max": {"regex": "none", "alertText": "* 最大值为 "},
                "past": {"regex": "none", "alertText": "* 日期必需早于 "},
                "future": {"regex": "none", "alertText": "* 日期必需晚于 "},
                "maxCheckbox": {"regex": "none", "alertText": "* 最多选取 ", "alertText2": " 个项目"},
                "minCheckbox": {"regex": "none", "alertText": "* 请选择 ", "alertText2": " 个项目"},
                "equals": {"regex": "none", "alertText": "* 请输入与上面相同的密码"},
                "creditCard": {"regex": "none", "alertText": "* 无效的信用卡号码"},
                "mobile": {"regex": /^1\d{10}$/, "alertText": "* 无效的手机号码"},
                "idCard": {"regex": /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/, "alertText": "* 身份证长度为15或18个字符"},
                "phone": {
                    "regex": /^([\+][0-9]{1,3}([ \.\-])?)?([\(][0-9]{1,6}[\)])?([0-9 \.\-]{1,32})(([A-Za-z \:]{1,11})?[0-9]{1,4}?)$/,
                    "alertText": "* 无效的电话号码"
                },
                "email": {
                    "regex": /^[a-zA-Z0-9_\.\-]+\@([a-zA-Z0-9\-]+\.)+[a-zA-Z0-9]{2,4}$/,
                    "alertText": "* 无效的邮件地址"
                },
                "integer": {"regex": /^[\-\+]?\d+$/, "alertText": "* 不是有效的整数"},
                "number": {
                    "regex": /^[\-\+]?((([0-9]{1,3})([,][0-9]{3})*)|([0-9]+))?([\.]([0-9]+))?$/,
                    "alertText": "* 无效的数字"
                },
                "score": {"regex": /^((100)|(\d{1,2}))$/, "alertText": "* 无效的数字"},
                "date": {
                    "regex": /^\d{4}[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])$/,
                    "alertText": "* 无效的日期，格式必需为 YYYY-MM-DD"
                },
                "ipv4": {
                    "regex": /^((([01]?[0-9]{1,2})|(2[0-4][0-9])|(25[0-5]))[.]){3}(([0-1]?[0-9]{1,2})|(2[0-4][0-9])|(25[0-5]))$/,
                    "alertText": "* 无效的 IP 地址"
                },
                "url": {
                    "regex": /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i,
                    "alertText": "* Invalid URL"
                },
                "onlyNumberSp": {"regex": /^[0-9\ ]+$/, "alertText": "* 只能填数字"},
                "onlyNumberGreaterZero": {"regex": /^[1-9]{1,1}[0-9\ ]{0,7}$/, "alertText": "* 只能填大于零小于一百万的数字"},
                "onlyLetterSp": {"regex": /^[a-zA-Z\ \']+$/, "alertText": "* 只接受英文字母大小写"},
                "onlyLetterAccentSp": {"regex": /^[a-z\u00C0-\u017F\ ]+$/i, "alertText": "* 只接受英文字母大小写"},
                "onlyLetterNumber": {"regex": /^[0-9a-zA-Z]+$/, "alertText": "* 不接受特殊字符"},
                "password": {"regex": /[\u0021-\u007E]/, "alertText": "* 不接受特殊字符"},
                "ajaxUserCall": {
                    "url": "ajaxValidateFieldUser",
                    "extraData": "name=eric",
                    "alertText": "* 此名称已被其他人使用",
                    "alertTextLoad": "* 正在确认名称是否有其他人使用，请稍等。"
                },
                "ajaxUserCallPhp": {
                    "url": "phpajax/ajaxValidateFieldUser.php",
                    "extraData": "name=eric",
                    "alertTextOk": "* 此帐号名称可以使用",
                    "alertText": "* 此名称已被其他人使用",
                    "alertTextLoad": "* 正在确认帐号名称是否有其他人使用，请稍等。"
                },
                "ajaxNameCall": {
                    "url": "ajaxValidateFieldName",
                    "alertText": "* 此名称可以使用",
                    "alertTextOk": "* 此名称已被其他人使用",
                    "alertTextLoad": "* 正在确认名称是否有其他人使用，请稍等。"
                },
                "ajaxNameCallPhp": {
                    "url": "phpajax/ajaxValidateFieldName.php",
                    "alertText": "* 此名称已被其他人使用",
                    "alertTextLoad": "* 正在确认名称是否有其他人使用，请稍等。"
                },
                "validate2fields": {"alertText": "* 请输入 HELLO"},
                "dateFormat": {
                    "regex": /^\d{4}[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])$|^(?:(?:(?:0?[13578]|1[02])(\/|-)31)|(?:(?:0?[1,3-9]|1[0-2])(\/|-)(?:29|30)))(\/|-)(?:[1-9]\d\d\d|\d[1-9]\d\d|\d\d[1-9]\d|\d\d\d[1-9])$|^(?:(?:0?[1-9]|1[0-2])(\/|-)(?:0?[1-9]|1\d|2[0-8]))(\/|-)(?:[1-9]\d\d\d|\d[1-9]\d\d|\d\d[1-9]\d|\d\d\d[1-9])$|^(0?2(\/|-)29)(\/|-)(?:(?:0[48]00|[13579][26]00|[2468][048]00)|(?:\d\d)?(?:0[48]|[2468][048]|[13579][26]))$/,
                    "alertText": "* 无效的日期格式"
                },
                "dateTimeFormat": {
                    "regex": /^\d{4}[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])\s+(1[012]|0?[1-9]){1}:(0?[1-5]|[0-6][0-9]){1}:(0?[0-6]|[0-6][0-9]){1}\s+(am|pm|AM|PM){1}$|^(?:(?:(?:0?[13578]|1[02])(\/|-)31)|(?:(?:0?[1,3-9]|1[0-2])(\/|-)(?:29|30)))(\/|-)(?:[1-9]\d\d\d|\d[1-9]\d\d|\d\d[1-9]\d|\d\d\d[1-9])$|^((1[012]|0?[1-9]){1}\/(0?[1-9]|[12][0-9]|3[01]){1}\/\d{2,4}\s+(1[012]|0?[1-9]){1}:(0?[1-5]|[0-6][0-9]){1}:(0?[0-6]|[0-6][0-9]){1}\s+(am|pm|AM|PM){1})$/,
                    "alertText": "* 无效的日期或时间格式",
                    "alertText2": "可接受的格式： ",
                    "alertText3": "mm/dd/yyyy hh:mm:ss AM|PM 或 ",
                    "alertText4": "yyyy-mm-dd hh:mm:ss AM|PM"
                },
                "timeFormat": {
                    "regex": /^(([0-1][0-9])|2[0-3]):[0-5][0-9]$/,
                    "alertText": "* 无效的时间格式",
                    "alertText2": "可接受的格式： ",
                    "alertText3": "hh:mm:ss"
                }
            };
        }
    };
    $.validationEngineLanguage.newLang();
})(jQuery);
function specialChar(field, rules, i, options) {
    var value = field.val();
    var pattern = new RegExp("[（！@#￥%……&*（）——+{}：”|》？，。、）\"\'\\\\]");
    if (!pattern.test(value)) {
        return true;
    } else {
        return "含有特殊字符";
    }
}