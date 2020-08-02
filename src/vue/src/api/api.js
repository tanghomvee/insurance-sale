import axios from 'axios';
import util from '../common/js/util';
import da from "element-ui/lib/locale/lang/da";


let base = 'http://localhost';

export const ajax = (url , method  , headers , params , calller) => {
    let data = null;
    if (headers && headers['Content-Type'] === 'application/json'){
        data =params;
        params = null;
    }
    return axios({
        "method":method || "post",
        "url":url,
        "params":params,
        "data":data,
        "headers" :headers || {
            'Content-Type':'application/x-www-form-urlencoded'
        }
    }).then(function(res){
        let msg = null;
        if(!res.data){
            msg = "系统错误";

        }else if(res.data.code != "success"){
            if(res.data.code == "login"){
                calller.$router.push({ path: '/login' });
                return;
            }
            msg = res.data.msg || "未知错误";
        }

        if (msg){
            if(calller){

                util.Msg.error(calller  ,msg);
            }
            return;
        }


        return res.data;
    }).catch(function (error) {
        console.log(error);
        if(calller){

            util.Msg.error(calller ,"系统错误");
        }
    });
};


export const requestLogin = (params,caller) => {
    return ajax("/saleman/login" , null ,{
        'Content-Type':'application/json'
    } ,params , caller);
};
export const setting = (params,caller) => {
    return ajax("/saleman/setting" , null ,null ,params , caller);
};

/**保险销售平台*/
export const listCustomer = (pathParams,params,caller) => {
    return ajax("/customer/list" + pathParams , "POST" , {'Content-Type':'application/json'} ,params , caller);
};
export const oneCustomer = (params,caller) => {
    return ajax("/customer/get" , "GET" , null ,params , caller);
};
export const addAppointment = (params,caller) => {
    return ajax("/appointment/add", "POST" , {'Content-Type':'application/json'} ,params , caller);
};
export const editAppointment = (params,caller) => {
    return ajax("/appointment/edit", "POST" , {'Content-Type':'application/json'} ,params , caller);
};
export const listAppointment = (pathParams,params,caller) => {
    return ajax("/appointment/list" + pathParams , "POST" , {'Content-Type':'application/json'} ,params , caller);
};
export const countAppointment = (params,caller) => {
    return ajax("/appointment/count"  , "GET" , null ,params , caller);
};
export const expiredAppointment = (params,caller) => {
    return ajax("/appointment/expired"  , "GET" , null ,params , caller);
};