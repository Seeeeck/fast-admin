(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-4aee7cf4"],{2017:function(e,t,o){"use strict";o("cafe")},6023:function(e,t,o){"use strict";o("bc38")},"9ed6":function(e,t,o){"use strict";o.r(t);var n=function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("div",{staticClass:"login-container"},[o("el-form",{ref:"loginForm",staticClass:"login-form",attrs:{model:e.loginForm,rules:e.loginRules,"auto-complete":"on","label-position":"left"}},[o("div",{staticClass:"title-container"},[o("h3",{staticClass:"title"},[e._v("Login Form")])]),o("el-form-item",{attrs:{prop:"username"}},[o("span",{staticClass:"svg-container"},[o("svg-icon",{attrs:{"icon-class":"user"}})],1),o("el-input",{ref:"username",attrs:{placeholder:"Username",name:"username",type:"text",tabindex:"1","auto-complete":"on"},model:{value:e.loginForm.username,callback:function(t){e.$set(e.loginForm,"username",t)},expression:"loginForm.username"}})],1),o("el-form-item",{attrs:{prop:"password"}},[o("span",{staticClass:"svg-container"},[o("svg-icon",{attrs:{"icon-class":"password"}})],1),o("el-input",{key:e.passwordType,ref:"password",attrs:{type:e.passwordType,placeholder:"Password",name:"password",tabindex:"2","auto-complete":"on"},model:{value:e.loginForm.password,callback:function(t){e.$set(e.loginForm,"password",t)},expression:"loginForm.password"}}),o("span",{staticClass:"show-pwd",on:{click:e.showPwd}},[o("svg-icon",{attrs:{"icon-class":"password"===e.passwordType?"eye":"eye-open"}})],1)],1),o("el-form-item",{attrs:{prop:"code"}},[o("span",{staticClass:"svg-container"},[o("svg-icon",{attrs:{"icon-class":"captcha"}})],1),o("el-input",{ref:"code",attrs:{placeholder:"Captcha",name:"code",tabindex:"2","auto-complete":"on",type:"text"},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleLogin(t)}},scopedSlots:e._u([{key:"suffix",fn:function(){return[o("img",{staticStyle:{cursor:"pointer"},attrs:{src:e.image},on:{click:e.getCaptcha}})]},proxy:!0}]),model:{value:e.loginForm.code,callback:function(t){e.$set(e.loginForm,"code",t)},expression:"loginForm.code"}})],1),o("el-button",{staticStyle:{width:"100%","margin-bottom":"30px"},attrs:{loading:e.loading,type:"primary"},nativeOn:{click:function(t){return t.preventDefault(),e.handleLogin(t)}}},[e._v("Login")])],1)],1)},s=[],a=o("b775");function r(){return Object(a["a"])({url:"/sys/captcha",method:"post",params:{t:new Date}})}var i={name:"Login",data:function(){var e=function(e,t,o){t.length<5?o(new Error("The password can not be less than 5 digits")):o()};return{loginForm:{username:"admin1",password:"admin",key:"",code:""},image:"",loginRules:{username:[{required:!0,trigger:"blur"}],password:[{required:!0,trigger:"blur",validator:e}]},loading:!1,passwordType:"password",redirect:void 0}},watch:{$route:{handler:function(e){this.redirect=e.query&&e.query.redirect},immediate:!0}},created:function(){this.getCaptcha()},methods:{getCaptcha:function(){var e=this;r().then((function(t){var o=t.data,n=o.key,s=o.image;e.loginForm.key=n,e.image=s}))},showPwd:function(){var e=this;"password"===this.passwordType?this.passwordType="":this.passwordType="password",this.$nextTick((function(){e.$refs.password.focus()}))},handleLogin:function(){var e=this;this.$refs.loginForm.validate((function(t){if(!t)return console.log("error submit!!"),!1;e.loading=!0,e.$store.dispatch("user/login",e.loginForm).then((function(){e.$router.push({path:e.redirect||"/"}),e.loading=!1})).catch((function(t){1101===t.code&&e.getCaptcha(),e.loading=!1}))}))}}},c=i,l=(o("2017"),o("6023"),o("2877")),d=Object(l["a"])(c,n,s,!1,null,"ea29dcdc",null);t["default"]=d.exports},bc38:function(e,t,o){},cafe:function(e,t,o){}}]);