namespace java nowait.service.cache.api

const string CLIENT_ID_ZF = "ZF" //支付
const string CLIENT_ID_PD = "PD" //排队
const string CLIENT_ID_YD = "YD" //预订
const string CLIENT_ID_PS = "PS" //POS机

service CacheService{

    string ping(),

    string setCache(1:string key,2:string value),

    string getCache(1:string key),

    string setCacheWithClientId(1:string clientId,2:string key,3:string value),

    string getCacheWithClientId(1:string clientId,2:string key),

}