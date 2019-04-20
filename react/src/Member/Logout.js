import React from 'react';
import Cookie from '../cookie';
import Utils from '../utils';

class Logout extends React.Component {

    constructor(props){
        super(props);
        this.clear();
        alert("Logout successfully");
        window.location.href = Utils.url('member-login');
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
