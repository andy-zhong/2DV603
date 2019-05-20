import React from 'react';
import ReactDOM from 'react-dom';
import * as serviceWorker from './serviceWorker';
import { BrowserRouter, Route} from 'react-router-dom';

import Utils from './utils';
import IndexShow from './Index/Show';
import MemberLogin from './Member/Login';
import MemberLogout from './Member/Logout';
import DescriptionSubmit from './Description/Submit';
import ManageDescription from './Description/Manage';
import ProjectPlanSubmit from './Plan/Submit';
import ProjectPlanManage from './Plan/Manage';
import ProjectReportSubmit from './Report/Submit';
import StudentList from './Student/List';
import SupervisorList from './Supervisor/List';
import OpponentList from './Opponent/List';
import ReaderList from './Reader/List';

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
            <Route excat path={Utils.url('description-submit')} component={DescriptionSubmit}></Route>
            <Route excat path={Utils.url('description-manage')} component={ManageDescription}></Route>
            <Route excat path={Utils.url('projectplan-submit')} component={ProjectPlanSubmit}></Route>
            <Route excat path={Utils.url('projectplan-manage')} component={ProjectPlanManage}></Route>
            <Route excat path={Utils.url('projectreport-submit')} component={ProjectReportSubmit}></Route>
            <Route excat path={Utils.url('student-list')} component={StudentList}></Route>
            <Route excat path={Utils.url('supervisor-list')} component={SupervisorList}></Route>
            <Route excat path={Utils.url('opponent-list')} component={OpponentList}></Route>
            <Route excat path={Utils.url('reader-list')} component={ReaderList}></Route>
        </div>
    </BrowserRouter>
), document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister();
