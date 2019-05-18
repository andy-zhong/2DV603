import Config from "./config";
import Cookie from './cookie';

let utils = {};

utils.url = function(url){
    url = "/"+url.replace("-", "/");
    url += ".do";
    return url;
}

utils.jsonToForm = function(json){
    let form = "";
    for(let j in json){
        form += j+"="+json[j]+"&"
    }
    if(form.length>0) form = form.substring(0, form.length-1);
    return form;
}

utils.request = function(url, body, success, fail){
    fetch(url, {
        method: "POST",
        mode: "cors",
        body: body,
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
        },
    }).then(res=>res.text()).then(data=>{
        data = data ? JSON.parse(data) : null;
        if(data) {
            if(data.code===1 && success) success(data.message);
            if(data.code===0 && fail) fail(data.message);
        }
    });
}

utils.upload = function(url, body, success, fail){
    fetch(url, {
        method: "POST",
        mode: "cors",
        body: body,
        headers: {

        },
    }).then(res=>res.text()).then(data=>{
        data = data ? JSON.parse(data) : null;
        if(data) {
            if(data.code===1 && success) success(data.message);
            if(data.code===0 && fail) fail(data.message);
        }
    });
}

utils.checkMember = function(callback){
    let $this = this;
    let c = this.getLoginCookie();
    this.request(Config.requestUrl+"member/read", "cookie=" + encodeURIComponent(c), function(data){
        if(callback) callback(data);
    }, function(){
        alert("Please login first");
        window.location.href = $this.url('member/login');
    });
}

utils.getLoginCookie = function(){
    return Cookie.load("loginCookie");
}

export default utils;
