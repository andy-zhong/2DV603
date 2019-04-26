let cookie = {

    save : function(name,value,time) {
        if(!time) time = 86400;
        let exp = new Date();
        exp.setTime(exp.getTime() + time * 1000);
        document.cookie = name+"="+value+";expires="+exp.toGMTString()+';path=/';
    },

    load: function(name) {
        let strCookie = document.cookie;
        let arr = strCookie.split(';');
        for (let i = 0; i < arr.length; i++) {
            let t = arr[i].split("=");
            if(t[0].trim() === name) {
                return t[1];
            }
        };
        return null;
    },

    remove: function(name){
        this.save(name, "", -100000);
    }

}

export default cookie;
