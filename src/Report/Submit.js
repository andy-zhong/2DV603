import React from 'react';

import Utils from '../utils';
import Config from '../config';
import './Submit.css';

import Header from '../Public/Header'
import Sider from '../Public/Sider'

class Submit extends React.Component {

    constructor(props){
        super(props);
        document.title = 'Project Report Submit';
        let $this = this;
        $this.state = {
            file: null,
            member: {groupObj: {}}
        };
        Utils.checkMember(function(res){
            $this.setState({
                member: res
            });
        });
        this.fileSelect = this.fileSelect.bind(this);
        this.submit = this.submit.bind(this);
    }

    submit() {
        if(window.confirm('Confirm to submit?')){
            const formData = new FormData();
            formData.append('file', this.state.file);
            formData.append('type', 'projectReport');
            formData.append('member', Utils.getLoginCookie());
            formData.append('sid', '');
            Utils.upload(Config.requestUrl+'submit', formData, function(res){
                alert(res);
                window.location.reload();
            }, function(err){
                alert(err);
            });
        }
    }

    fileSelect(e) {
        let file = e.target.files[0];
        this.setState({
            file: file
        });
    }

    render(){
        let member = this.state.member;
        return (
            <div>
                <Sider member={member} active1="Project Report" active2="Submit Project Report"></Sider>
                <Header title1="Project Report" title2="Submit Project Report"></Header>

                <div className="wrap">

                    <table className="table table-striped">
                        <thead>
                        <tr>
                            <th>Submission</th>
                            <th>Project report</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>Submission status</td>
                            <td>Submitted for grading/Not submit</td>
                        </tr>
                        <tr>
                            <td>Submission status</td>
                            <td>Submitted for grading/Not submit</td>
                        </tr>
                        <tr>
                            <td>Deadline</td>
                            <td>date</td>
                        </tr>
                        <tr>
                            <td>Last modified</td>
                            <td>detail time when last submission</td>
                        </tr>
                        <tr>
                            <td>Grading status</td>
                            <td>A/B/C/D/E/F／Not graded</td>
                        </tr>
                        </tbody>
                    </table>

                    <input type="file" className="form-control" placeholder="Choose your file" name="file" onChange={this.fileSelect} />
                    <button className="btn btn-primary" onClick={this.submit}>Submit</button>
                </div>
            </div>
        )
    }
}

export default Submit;
