import React from "react";

import './Footer.css';

class Footer extends React.Component {

    render(){
        return (
            <div className="foot">
                <div className="tac"><img src={require('../Asset/Img/ft.png')} alt="" /></div>
                <div className="cop">
                    <a href="/cn/nav/184258">关于我们</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a
                    href="/cn/nav/184253">常见问题</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a
                    href="/cn/nav/184278">联系我们</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a
                    href="/cn/nav/184254">代理合作</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a
                    href="/cn/nav/184256">代理协议</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a
                    href="/cn/nav/184252">存款帮助</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a
                    href="/cn/nav/184255">取款帮助</a> Copyright © 新葡京娱乐 Reserved
                </div>
            </div>
        )
    }
}

export default Footer;