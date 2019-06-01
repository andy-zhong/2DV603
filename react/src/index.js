import React from 'react';
import ReactDOM from 'react-dom';
import * as serviceWorker from './serviceWorker';
import { BrowserRouter, Route} from 'react-router-dom';

import Utils from './utils';
import Loading from './Public/Loading';

import IndexShow from './Index/Show';
import MemberLogin from './Member/Login';
import MemberAdd from './Member/Add';
import MemberEdit from './Member/Edit';
import MemberLogout from './Member/Logout';
import SubmissionSubmit from './Submission/Submit';
import SubmissionManage from './Submission/Manage';
import SubmissionView from './Submission/View';
import StudentList from './Student/List';
import StudentSubmission from './Student/Submission';
import SupervisorList from './Supervisor/List';
import ReaderList from './Reader/List';
import ScheduleShow from './Schedule/Show';
import ScheduleEdit from './Schedule/Edit';
import ScheduleAdd from './Schedule/Add';

import 'bootstrap/dist/css/bootstrap.css';
import './Asset/Css/icon.css'
import './index.css';

// Loading Animation
let loading = document.createElement('div');
ReactDOM.render(<Loading />, loading);
document.body.appendChild(loading);

ReactDOM.render((
    <BrowserRouter>
        <div>
            <Route exact path="/" component={IndexShow}></Route>
            <Route exact path={Utils.url('index-show')} component={IndexShow}></Route>
            <Route excat path={Utils.url('member-login')} component={MemberLogin}></Route>
            <Route excat path={Utils.url('member-logout')} component={MemberLogout}></Route>
            <Route excat path={Utils.url('member-add')} component={MemberAdd}></Route>
            <Route excat path={Utils.url('member-edit-:id')} component={MemberEdit}></Route>
            <Route excat path={Utils.url('submission-submit-:type')} component={SubmissionSubmit}></Route>
            <Route excat path={Utils.url('submission-manage-:type')} component={SubmissionManage}></Route>
            <Route excat path={Utils.url('submission-view-:type')} component={SubmissionView}></Route>
            <Route excat path={Utils.url('student-list')} component={StudentList}></Route>
            <Route excat path={Utils.url('student-submission-:id')} component={StudentSubmission}></Route>
            <Route excat path={Utils.url('supervisor-list')} component={SupervisorList}></Route>
            <Route excat path={Utils.url('reader-list')} component={ReaderList}></Route>
            <Route excat path={Utils.url('schedule-show')} component={ScheduleShow}></Route>
            <Route excat path={Utils.url('schedule-add')} component={ScheduleAdd}></Route>
            <Route excat path={Utils.url('schedule-edit-:id')} component={ScheduleEdit}></Route>
        </div>
    </BrowserRouter>
), document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister();
