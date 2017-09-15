package com.whh.common.api.aliyun;

import com.alibaba.fastjson.JSONObject;
import com.whh.common.api.interfaces.IWeatherApi;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huahui.wu on 2017/9/14.
 */
public class AliIWeatherApi extends AliyunApi{

    /**
     * 查询地名对应的id
     *
     * @param area STRING   必选  地区名称
     * @return {
     * "showapi_res_code": 0,
     * "showapi_res_error": "",
     * "showapi_res_body": {
     * "list": [
     * {
     * "area": "丽江",
     * "areaid": "101291401",
     * "cityInfo": {
     *      "c1": "101291401",
     *      "c10": "2",
     *      "c11": "0888",
     *      "c12": "674100",
     *      "c15": "2394",
     *      "c16": "AZ9888",
     *      "c17": "+8",
     *      "c2": "lijiang",
     *      "c3": "丽江",
     *      "c4": "lijiang",
     *      "c5": "丽江",
     *      "c6": "yunnan",
     *      "c7": "云南",
     *      "c8": "china",
     *      "c9": "中国",
     *      "latitude": 26.903,
     *      "longitude": 100.222
     *   },
     * "distric": "丽江",
     * "prov": "云南"
     *  }
     * ],
     *  "ret_code": 0
     * }
     * }
     */
    public static JSONObject getIdByArea(String area) {
        String host = "http://saweather.market.alicloudapi.com";
        String path = "/area-to-id";
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("area", area);

        JSONObject jsonObject = request(host, path, methodGet, headers, querys);

        return jsonObject;

    }

    /**
     * IP查询7天预报详情
     *
     * @param querys
     * @return
     * {
        "showapi_res_error": "",
        "showapi_res_body": {
            "now": {
                "temperature_time": "11:30",
                "sd": "94%",
                "aqi": "39",
                "weather": "阴",
                "temperature": "21",
                "aqiDetail": {
                    "area": "杭州",
                    "no2": "35",
                    "o3": "48",
                    "num": "69",
                    "so2": "7",
                    "pm2_5": "23",
                    "aqi": "39",
                    "pm10": "38",
                    "primary_pollutant": "",
                    "co": "0.973",
                    "quality": "优质",
                    "o3_8h": "63"
                },
                "wind_direction": "北风",
                "weather_pic": "http://app1.showapi.com/weather/icon/day/02.png",
                "weather_code": "02",
                "wind_power": "3级"
            },
            "cityInfo": {
                "c11": "0571",
                "c10": "1",
                "c12": "310000",
                "c15": "43",
                "latitude": 30.319,
                "c17": "+8",
                "c16": "AZ9571",
                "c1": "101210101",
                "c2": "hangzhou",
                "c3": "杭州",
                "c4": "hangzhou",
                "c5": "杭州",
                "c6": "zhejiang",
                "c7": "浙江",
                "c8": "china",
                "c9": "中国",
                "longitude": 120.165
            },
            "time": "20170915073000",
            "f1": {
                "jiangshui": "73%",
                "air_press": "1012 hPa",
                "weekday": 5,
                "night_wind_direction": "北风",
                "night_air_temperature": "22",
                "night_weather_pic": "http://app1.showapi.com/weather/icon/night/03.png",
                "night_weather": "阵雨",
                "night_weather_code": "03",
                "day_weather_code": "03",
                "ziwaixian": "最弱",
                "day_weather": "阵雨",
                "day_wind_power": "5-6级 10.8~13.8m/s",
                "day_weather_pic": "http://app1.showapi.com/weather/icon/day/03.png",
                "day_air_temperature": "26",
                "day_wind_direction": "北风",
                "night_wind_power": "5-6级 10.8~13.8m/s",
                "sun_begin_end": "05:43|18:06",
                "day": "20170915"
            },
            "f2": {
                "jiangshui": "13%",
                "air_press": "1012 hPa",
                "weekday": 6,
                "night_wind_direction": "西北风",
                "night_air_temperature": "19",
                "night_weather_pic": "http://app1.showapi.com/weather/icon/night/00.png",
                "night_weather": "晴",
                "night_weather_code": "00",
                "day_weather_code": "01",
                "ziwaixian": "最弱",
                "day_weather": "多云",
                "day_wind_power": "4-5级 8.0~10.7m/s",
                "day_weather_pic": "http://app1.showapi.com/weather/icon/day/01.png",
                "day_air_temperature": "27",
                "day_wind_direction": "西北风",
                "night_wind_power": "3-4级 5.5~7.9m/s",
                "sun_begin_end": "05:44|18:04",
                "day": "20170916"
            },
            "ret_code": 0,
            "f3": {
                "jiangshui": "0%",
                "air_press": "1012 hPa",
                "weekday": 7,
                "night_wind_direction": "北风",
                "night_air_temperature": "19",
                "night_weather_pic": "http://app1.showapi.com/weather/icon/night/00.png",
                "night_weather": "晴",
                "night_weather_code": "00",
                "day_weather_code": "00",
                "ziwaixian": "最弱",
                "day_weather": "晴",
                "day_wind_power": "微风 <5.4m/s",
                "day_weather_pic": "http://app1.showapi.com/weather/icon/day/00.png",
                "day_air_temperature": "29",
                "day_wind_direction": "北风",
                "night_wind_power": "微风 <5.4m/s",
                "sun_begin_end": "05:44|18:03",
                "day": "20170917"
            },
            "showapi_treemap": true
        },
        "showapi_res_code": 0
    }
     */
    public static JSONObject getWeatherByIp(Map<String, String> querys) {
        String host = "http://saweather.market.alicloudapi.com";
        String path = "/ip-to-weather";

        JSONObject jsonObject = request(host, path, methodGet, headers, querys);

        if (jsonObject.getInteger("showapi_res_code") != 0) {
            log.error("获取天气信息失败：" + jsonObject.getString("showapi_res_error"));
        }

        return jsonObject.getJSONObject("showapi_res_body");
    }

    /**
     *
     * @param querys
     *       from    STRING  必选      输入的坐标类型： 1：GPS设备获取的角度坐标; 2：GPS获取的米制坐标、sogou地图所用坐标; 3：google地图、soso地图、aliyun地图、mapabc地图和amap地图所用坐标 4：3中列表地图坐标对应的米制坐标 5：百度地图采用的经纬度坐标 6：百度地图采用的米制坐标 7：mapbar地图坐标; 8：51地图坐标
     *       lat	STRING    必选    纬度值
     *       lng    STRING    必选    经度值
     *       need3HourForcast    STRING    可选    是否需要当天每3/6小时一次的天气预报列表。1为需要，0为不需要。
     *       needAlarm    STRING    可选    是否需要天气预警。1为需要，0为不需要。
     *       needHourData    STRING    可选    是否需要每小时数据的累积数组。由于本系统是半小时刷一次实时状态，因此实时数组最大长度为48。每天0点长度初始化为0. 1为需要 0为不
     *       needIndex    STRING    可选    是否需要返回指数数据，比如穿衣指数、紫外线指数等。1为返回，0为不返回。
     *       needMoreDay    STRING    可选    是否需要返回7天数据中的后4天。1为返回，0为不返回。
     * @return
     *  {
          "showapi_res_code": 0,
          "showapi_res_error": "",
          "showapi_res_body": {
            "f6": {
              "day_weather": "小雨",
              "night_weather": "小雨",
              "night_weather_code": "07",
              "index": {
                "cold": {
                  "title": "少发",
                  "desc": "无明显降温，感冒机率较低。"
                },
                "clothes": {
                  "title": "舒适",
                  "desc": "建议穿长袖衬衫单裤等服装。"
                },
                "uv": {
                  "title": "最弱",
                  "desc": "辐射弱，涂擦SPF8-12防晒护肤品。"
                },
                "aqi": {
                  "title": "良",
                  "desc": "气象条件有利于空气污染物扩散。"
                },
                "wash_car": {
                  "title": "不宜",
                  "desc": "有雨，雨水和泥水会弄脏爱车。"
                },
                "travel": {
                  "title": "较不宜",
                  "desc": "有降水，推荐您在室内进行休闲运动。"
                }
              },
              "night_wind_power": "微风<10m/h",
              "day_wind_power": "微风<10m/h",
              "day_weather_code": "07",
              "sun_begin_end": "06:28|20:17",
              "day_weather_pic": "http://app1.showapi.com/weather/icon/day/07.png",
              "weekday": 1,
              "night_air_temperature": "15",
              "day_air_temperature": "24",
              "day_wind_direction": "无持续风向",
              "day": "20160704",
              "night_weather_pic": "http://app1.showapi.com/weather/icon/night/07.png",
              "night_wind_direction": "无持续风向"
            },
            "f7": {
              "day_weather": "小雨",
              "night_weather": "中雨",
              "night_weather_code": "08",
              "index": {
                "cold": {
                  "title": "少发",
                  "desc": "无明显降温，感冒机率较低。"
                },
                "clothes": {
                  "title": "较舒适",
                  "desc": "建议穿薄外套或牛仔裤等服装。"
                },
                "uv": {
                  "title": "最弱",
                  "desc": "辐射弱，涂擦SPF8-12防晒护肤品。"
                },
                "wash_car": {
                  "title": "较不宜",
                  "desc": "有降水，推荐您在室内进行休闲运动。"
                },
                "travel": {
                  "title": "良",
                  "desc": "气象条件有利于空气污染物扩散。"
                }
              },
              "night_wind_power": "微风<10m/h",
              "day_wind_power": "微风<10m/h",
              "day_weather_code": "07",
              "sun_begin_end": "06:28|20:17",
              "day_weather_pic": "http://app1.showapi.com/weather/icon/day/07.png",
              "weekday": 2,
              "night_air_temperature": "15",
              "day_air_temperature": "23",
              "day_wind_direction": "无持续风向",
              "day": "20160705",
              "night_weather_pic": "http://app1.showapi.com/weather/icon/night/08.png",
              "night_wind_direction": "无持续风向"
            },
            "time": "20160629113000",
            "ret_code": 0,
            "cityInfo": {
              "c6": "yunnan",
              "c5": "丽江",
              "c4": "lijiang",
              "c3": "丽江",
              "c9": "中国",
              "c8": "china",
              "c7": "云南",
              "c17": "+8",
              "c16": "AZ9888",
              "c1": "101291401",
              "c2": "lijiang",
              "c11": "0888",
              "longitude": 100.222,
              "c10": "2",
              "latitude": 26.903,
              "c12": "674100",
              "c15": "2394"
            },
            "now": {
              "aqiDetail": {
                "co": 0.38,
                "so2": 8,
                "area": "丽江",
                "o3": 42,
                "no2": 9,
                "area_code": "lijiang",
                "quality": "优",
                "aqi": 19,
                "pm10": 18,
                "pm2_5": 12,
                "o3_8h": 37,
                "primary_pollutant": ""
              },
              "weather_code": "03",
              "wind_direction": "西北风",
              "temperature_time": "16:01",
              "wind_power": "1级",
              "aqi": 19,
              "sd": "70%",
              "weather_pic": "http://appimg.showapi.com/images/weather/icon/day/03.png",
              "weather": "阵雨",
              "temperature": "21"
            },
            "f1": {
              "day_weather": "小雨",
              "night_weather": "小雨",
              "night_weather_code": "07",
              "index": {
                "yh": {
                  "title": "较不适宜",
                  "desc": "建议尽量不要去室外约会。"
                },
                "ls": {
                  "title": "不宜",
                  "desc": "降水可能会淋湿衣物，请选择在室内晾晒。"
                },
                "clothes": {
                  "title": "较舒适",
                  "desc": "建议穿薄外套或牛仔裤等服装。"
                },
                "dy": {
                  "title": "不宜",
                  "desc": "天气不好，不适宜垂钓。"
                },
                "beauty": {
                  "title": "保湿",
                  "desc": "请选用中性保湿型霜类化妆品。"
                },
                "xq": {
                  "title": "较差",
                  "desc": "雨水可能会使心绪无端地挂上轻愁。"
                },
                "travel": {
                  "title": "适宜",
                  "desc": "较弱降水和微风将伴您共赴旅程。"
                },
                "hc": {
                  "title": "不适宜",
                  "desc": "天气不好，建议选择别的娱乐方式。"
                },
                "zs": {
                  "title": "无",
                  "desc": "气温不高，中暑几率极低。"
                },
                "cold": {
                  "title": "较易发",
                  "desc": "降温幅度较大，预防感冒。"
                },
                "gj": {
                  "title": "较适宜",
                  "desc": "有降水，逛街需要带雨具。"
                },
                "uv": {
                  "title": "最弱",
                  "desc": "辐射弱，涂擦SPF8-12防晒护肤品。"
                },
                "cl": {
                  "title": "较不宜",
                  "desc": "室外锻炼请携带雨具。"
                },
                "glass": {
                  "title": "不需要",
                  "desc": "白天能见度差不需要佩戴太阳镜"
                },
                "wash_car": {
                  "title": "不宜",
                  "desc": "有雨，雨水和泥水会弄脏爱车。"
                },
                "aqi": {
                  "title": "中",
                  "desc": "无需特别防护"
                },
                "ac": {
                  "title": "较少开启",
                  "desc": "体感舒适，不需要开启空调。"
                },
                "mf": {
                  "title": "一般",
                  "desc": "建议选用防晒滋润型护发品或带遮阳帽。"
                },
                "ag": {
                  "title": "极不易发",
                  "desc": "无需担心过敏，可放心外出，享受生活。"
                },
                "pj": {
                  "title": "较适宜",
                  "desc": "适量的饮用啤酒，注意不要过量。"
                },
                "nl": {
                  "title": "较不适宜",
                  "desc": "建议夜生活最好在室内进行。"
                },
                "pk": {
                  "title": "不宜",
                  "desc": "天气不好，不适宜放风筝。"
                }
              },
              "air_press": "766 hPa",
              "jiangshui": "75%",
              "night_wind_power": "微风<10m/h",
              "day_wind_power": "微风<10m/h",
              "day_weather_code": "07",
              "sun_begin_end": "06:28|20:17",
              "ziwaixian": "最弱",
              "day_weather_pic": "http://app1.showapi.com/weather/icon/day/07.png",
              "weekday": 3,
              "night_air_temperature": "15",
              "day_air_temperature": "22",
              "day_wind_direction": "无持续风向",
              "day": "20160629",
              "night_weather_pic": "http://app1.showapi.com/weather/icon/night/07.png",
              "night_wind_direction": "无持续风向"
            },
            "f3": {
              "day_weather": "小雨",
              "night_weather": "中雨",
              "night_weather_code": "08",
              "index": {
                "cold": {
                  "title": "较易发",
                  "desc": "天较凉，增加衣服，注意防护。"
                },
                "clothes": {
                  "title": "较舒适",
                  "desc": "建议穿薄外套或牛仔裤等服装。"
                },
                "uv": {
                  "title": "最弱",
                  "desc": "辐射弱，涂擦SPF8-12防晒护肤品。"
                },
                "aqi": {
                  "title": "良",
                  "desc": "气象条件有利于空气污染物扩散。"
                },
                "wash_car": {
                  "title": "不宜",
                  "desc": "有雨，雨水和泥水会弄脏爱车。"
                },
                "travel": {
                  "title": "较不宜",
                  "desc": "有降水，推荐您在室内进行休闲运动。"
                }
              },
              "night_wind_power": "微风<10m/h",
              "day_wind_power": "微风<10m/h",
              "day_weather_code": "07",
              "sun_begin_end": "06:28|20:17",
              "day_weather_pic": "http://app1.showapi.com/weather/icon/day/07.png",
              "weekday": 5,
              "night_air_temperature": "13",
              "day_air_temperature": "21",
              "day_wind_direction": "无持续风向",
              "day": "20160701",
              "night_weather_pic": "http://app1.showapi.com/weather/icon/night/08.png",
              "night_wind_direction": "无持续风向"
            },
            "f2": {
              "day_weather": "小雨",
              "night_weather": "小雨",
              "night_weather_code": "07",
              "index": {
                "cold": {
                  "title": "少发",
                  "desc": "无明显降温，感冒机率较低。"
                },
                "clothes": {
                  "title": "舒适",
                  "desc": "建议穿长袖衬衫单裤等服装。"
                },
                "uv": {
                  "title": "最弱",
                  "desc": "辐射弱，涂擦SPF8-12防晒护肤品。"
                },
                "aqi": {
                  "title": "良",
                  "desc": "气象条件有利于空气污染物扩散。"
                },
                "wash_car": {
                  "title": "不宜",
                  "desc": "有雨，雨水和泥水会弄脏爱车。"
                },
                "travel": {
                  "title": "较不宜",
                  "desc": "有降水，推荐您在室内进行休闲运动。"
                }
              },
              "night_wind_power": "微风<10m/h",
              "day_wind_power": "微风<10m/h",
              "day_weather_code": "07",
              "sun_begin_end": "06:28|20:17",
              "day_weather_pic": "http://app1.showapi.com/weather/icon/day/07.png",
              "weekday": 4,
              "night_air_temperature": "16",
              "day_air_temperature": "24",
              "day_wind_direction": "无持续风向",
              "day": "20160630",
              "night_weather_pic": "http://app1.showapi.com/weather/icon/night/07.png",
              "night_wind_direction": "无持续风向"
            },
            "f5": {
              "day_weather": "阴",
              "night_weather": "小雨",
              "night_weather_code": "07",
              "index": {
                "cold": {
                  "title": "少发",
                  "desc": "无明显降温，感冒机率较低。"
                },
                "clothes": {
                  "title": "舒适",
                  "desc": "建议穿长袖衬衫单裤等服装。"
                },
                "uv": {
                  "title": "最弱",
                  "desc": "辐射弱，涂擦SPF8-12防晒护肤品。"
                },
                "aqi": {
                  "title": "较差",
                  "desc": "气象条件较不利于空气污染物扩散。。"
                },
                "wash_car": {
                  "title": "不宜",
                  "desc": "有雨，雨水和泥水会弄脏爱车。"
                },
                "travel": {
                  "title": "较适宜",
                  "desc": "较适宜进行各种户内外运动。"
                }
              },
              "night_wind_power": "微风<10m/h",
              "day_wind_power": "微风<10m/h",
              "day_weather_code": "02",
              "sun_begin_end": "06:28|20:17",
              "day_weather_pic": "http://app1.showapi.com/weather/icon/day/02.png",
              "weekday": 7,
              "night_air_temperature": "14",
              "day_air_temperature": "25",
              "day_wind_direction": "无持续风向",
              "day": "20160703",
              "night_weather_pic": "http://app1.showapi.com/weather/icon/night/07.png",
              "night_wind_direction": "无持续风向"
            },
            "f4": {
              "day_weather": "小雨",
              "night_weather": "多云",
              "night_weather_code": "01",
              "index": {
                "cold": {
                  "title": "较易发",
                  "desc": "天较凉，增加衣服，注意防护。"
                },
                "clothes": {
                  "title": "较舒适",
                  "desc": "建议穿薄外套或牛仔裤等服装。"
                },
                "uv": {
                  "title": "最弱",
                  "desc": "辐射弱，涂擦SPF8-12防晒护肤品。"
                },
                "aqi": {
                  "title": "良",
                  "desc": "气象条件有利于空气污染物扩散。"
                },
                "wash_car": {
                  "title": "不宜",
                  "desc": "有雨，雨水和泥水会弄脏爱车。"
                },
                "travel": {
                  "title": "较不宜",
                  "desc": "有降水，推荐您在室内进行休闲运动。"
                }
              },
              "night_wind_power": "微风<10m/h",
              "day_wind_power": "微风<10m/h",
              "day_weather_code": "07",
              "sun_begin_end": "06:28|20:17",
              "day_weather_pic": "http://app1.showapi.com/weather/icon/day/07.png",
              "weekday": 6,
              "night_air_temperature": "15",
              "day_air_temperature": "22",
              "day_wind_direction": "无持续风向",
              "day": "20160702",
              "night_weather_pic": "http://app1.showapi.com/weather/icon/night/01.png",
              "night_wind_direction": "无持续风向"
            }
          }
        }
     *
     */
    public static JSONObject getWeatherByGps(Map<String, String> querys) {
        String host = "http://saweather.market.alicloudapi.com";
        String path = "/gps-to-weather";
        querys.put("from", "5");
        querys.put("lat", "40.242266");
        querys.put("lng", "116.2278");
        querys.put("need3HourForcast", "0");
        querys.put("needAlarm", "0");
        querys.put("needHourData", "0");
        querys.put("needIndex", "0");
        querys.put("needMoreDay", "0");
        JSONObject jsonObject = request(host, path, methodGet, headers, querys);

        if (jsonObject.getInteger("showapi_res_code") != 0) {
            log.error("获取天气信息失败：" + jsonObject.getString("showapi_res_error"));
        }

        return jsonObject.getJSONObject("showapi_res_body");
    }


}
