import React from 'react';
import Cookie from '../cookie';
import Utils from '../utils';

class Logout extends React.Component {

    constructor(props){
        super(props);
        this.clear();
        Utils.alert("Logout successfully",function(){
            window.location.href = Utils.url('member-login');
        }, 1500);
    }

    clear(){
        Cookie.remove("loginCookie");
    }

    render(){
        return (
            <div>Bye</div>
        )
    }

}

export default Logout;
