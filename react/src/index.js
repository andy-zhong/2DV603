import React from 'react';
import ReactDOM from 'react-dom';
import * as serviceWorker from './serviceWorker';
import { BrowserRouter, Route} from 'react-router-dom';

import Utils from './utils';
import IndexShow from './Index/Show';
import MemberLogin from './Member/Login';
import MemberLogout from './Member/Logout';

import 'bootstrap/dist/css/bootstrap.css';
import './Asset/Css/icon.css'
import './index.css';

ReactDOM.render((
    <BrowserRouter>
        <div>
            <Route exact path="/" component={IndexShow}></Route>
            <Route exact path={Utils.url('index-show')} component={IndexShow}></Route>
            <Route excat path={Utils.url('member-login')} component={MemberLogin}></Route>
            <Route excat path={Utils.url('member-logout')} component={MemberLogout}></Route>
        </div>
    </BrowserRouter>
), document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister();
