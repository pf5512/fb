!function(a){function b(b,d){var i=b[h],j=i&&e[i];if(void 0===d)return j||c(b);if(j){if(d in j)return j[d];var k=g(d);if(k in j)return j[k]}return f.call(a(b),d)}function c(b,c,f){var i=b[h]||(b[h]=++a.uuid),j=e[i]||(e[i]=d(b));return void 0!==c&&(j[g(c)]=f),j}function d(b){var c={};return a.each(b.attributes||i,function(b,d){0==d.name.indexOf("data-")&&(c[g(d.name.replace("data-",""))]=a.zepto.deserializeValue(d.value))}),c}var e={},f=a.fn.data,g=a.camelCase,h=a.expando="Zepto"+ +new Date,i=[];a.fn.data=function(d,e){return void 0===e?a.isPlainObject(d)?this.each(function(b,e){a.each(d,function(a,b){c(e,a,b)})}):0 in this?b(this[0],d):void 0:this.each(function(){c(this,d,e)})},a.fn.removeData=function(b){return"string"==typeof b&&(b=b.split(/\s+/)),this.each(function(){var c=this[h],d=c&&e[c];d&&a.each(b||d,function(a){delete d[b?g(this):a]})})},["remove","empty"].forEach(function(b){var c=a.fn[b];a.fn[b]=function(){var a=this.find("*");return"remove"===b&&(a=a.add(this)),a.removeData(),c.call(this)}})}(window.Zepto),!function(a){var b={};b.cache={},a.tpl=function(a,c,d){var e=/[^\w\-\.:]/.test(a)?function(a,b){var c,d=[],f=[];for(c in a)d.push(c),f.push(a[c]);return new Function(d,e.code).apply(b||a,f)}:b.cache[a]=b.cache[a]||this.get(document.getElementById(a).innerHTML);return e.code=e.code||"var $parts=[]; $parts.push('"+a.replace(/\\/g,"\\\\").replace(/[\r\t\n]/g," ").split("<%").join("	").replace(/(^|%>)[^\t]*/g,function(a){return a.replace(/'/g,"\\'")}).replace(/\t=(.*?)%>/g,"',$1,'").split("	").join("');").split("%>").join("$parts.push('")+"'); return $parts.join('');",c?e(c,d):e},a.adaptObject=function(b,c,d,e,f,g){var h=b;if("string"!=typeof d){var i=a.extend({},c,"object"==typeof d&&d),j=!1;a.isArray(h)&&h.length&&"script"==a(h)[0].nodeName.toLowerCase()?(h=a(a.tpl(h[0].innerHTML,i)).appendTo("body"),j=!0):a.isArray(h)&&h.length&&""==h.selector?(h=a(a.tpl(h[0].outerHTML,i)).appendTo("body"),j=!0):a.isArray(h)||(h=a(a.tpl(e,i)).appendTo("body"),j=!0)}return h.each(function(){var b=a(this),e=b.data("fz."+g);e||b.data("fz."+g,e=new f(this,a.extend({},c,"object"==typeof d&&d),j)),"string"==typeof d&&e[d]()})}}(window.Zepto);!function(a){function b(b){return a.adaptObject(this,d,b,c,e,"loading")}var c='<div class="ui-loading-block show"><div class="ui-loading-cnt"><i class="ui-loading-bright"></i><p><%=content%></p></div></div>',d={content:"加载中..."},e=function(b,c,e){this.element=a(b),this._isFromTpl=e,this.option=a.extend(d,c),this.show()};e.prototype={show:function(){var b=a.Event("loading:show");this.element.trigger(b),this.element.show()},hide:function(){var b=a.Event("loading:hide");this.element.trigger(b),this.element.remove()}},a.fn.loading=a.loading=b}(window.Zepto);!function(a){function b(b){return a.adaptObject(this,d,b,c,e,"tips")}var c='<div class="ui-poptips ui-poptips-<%=type%>"><div class="ui-poptips-cnt"><i></i><%=content%></div></div>',d={content:"",stayTime:1e3,type:"info",callback:function(){}},e=function(b,c,e){var f=this;this.element=a(b),this._isFromTpl=e,this.elementHeight=a(b).height(),this.option=a.extend(d,c),a(b).css({"-webkit-transform":"translateY(-"+this.elementHeight+"px)"}),setTimeout(function(){a(b).css({"-webkit-transition":"all .5s"}),f.show()},20)};e.prototype={show:function(){var b=this;b.element.trigger(a.Event("tips:show")),this.element.css({"-webkit-transform":"translateY(0px)"}),b.option.stayTime>0&&setTimeout(function(){b.hide()},b.option.stayTime)},hide:function(){var b=this;b.element.trigger(a.Event("tips:hide")),this.element.css({"-webkit-transform":"translateY(-"+this.elementHeight+"px)"}),setTimeout(function(){b._isFromTpl&&b.element.remove()},500)}},a.fn.tips=a.tips=b}(window.Zepto);!function(a){function b(){return!1}function c(b){return a.adaptObject(this,e,b,d,f,"dialog")}var d='<div class="ui-dialog"><div class="ui-dialog-cnt"><div class="ui-dialog-bd"><div><h4><%=title%></h4><div><%=content%></div></div></div><div class="ui-dialog-ft ui-btn-group"><% for (var i = 0; i < button.length; i++) { %><% if (i == select) { %><button type="button" data-role="button"  class="select" id="dialogButton<%=i%>"><%=button[i]%></button><% } else { %><button type="button" data-role="button" id="dialogButton<%=i%>"><%=button[i]%></div><% } %><% } %></div></div></div>',e={title:"",content:"",button:["确认"],select:0,allowScroll:!1,callback:function(){}},f=function(b,c,d){this.option=a.extend(e,c),this.element=a(b),this._isFromTpl=d,this.button=a(b).find('[data-role="button"]'),this._bindEvent(),this.toggle()};f.prototype={_bindEvent:function(){var b=this;b.button.on("tap",function(){var c=a(b.button).index(a(this)),d=a.Event("dialog:action");d.index=c,b.element.trigger(d),b.hide.apply(b)})},toggle:function(){this.element.hasClass("show")?this.hide():this.show()},show:function(){var c=this;c.element.trigger(a.Event("dialog:show")),c.element.addClass("show"),this.option.allowScroll&&c.element.on("touchmove",b)},hide:function(){var c=this;c.element.trigger(a.Event("dialog:hide")),c.element.off("touchmove",b),c.element.removeClass("show"),c._isFromTpl&&c.element.remove()}},a.fn.dialog=a.dialog=c}(window.Zepto);!function(a){function b(b,c){this.wrapper="string"==typeof b?a(b)[0]:b,this.options={startX:0,startY:0,scrollY:!0,scrollX:!1,directionLockThreshold:5,momentum:!0,duration:300,bounce:!0,bounceTime:600,bounceEasing:"",preventDefault:!0,eventPassthrough:!0,freeScroll:!1,bindToWrapper:!0,resizePolling:60,disableMouse:!1,disableTouch:!1,disablePointer:!1,tap:!0,click:!1,preventDefaultException:{tagName:/^(INPUT|TEXTAREA|BUTTON|SELECT)$/},HWCompositing:!0,useTransition:!0,useTransform:!0};for(var e in c)this.options[e]=c[e];if(this.options.role||this.options.scrollX!==!1||(this.options.eventPassthrough="horizontal"),"slider"===this.options.role){if(this.options.scrollX=!0,this.options.scrollY=!1,this.options.momentum=!1,this.scroller=a(".ui-slider-content",this.wrapper)[0],a(this.scroller.children[0]).addClass("current"),this.currentPage=0,this.count=this.scroller.children.length,this.scroller.style.width=this.count+"00%",this.itemWidth=this.scroller.children[0].clientWidth,this.scrollWidth=this.itemWidth*this.count,this.options.indicator){for(var f='<ul class="ui-slider-indicators">',e=1;e<=this.count;e++)f+=1===e?'<li class="current">'+e+"</li>":"<li>"+e+"</li>";f+="</ul>",a(this.wrapper).append(f),this.indicator=a(".ui-slider-indicators",this.wrapper)[0]}}else"tab"===this.options.role?(this.options.scrollX=!0,this.options.scrollY=!1,this.options.momentum=!1,this.scroller=a(".ui-tab-content",this.wrapper)[0],this.nav=a(".ui-tab-nav",this.wrapper)[0],a(this.scroller.children[0]).addClass("current"),a(this.nav.children[0]).addClass("current"),this.currentPage=0,this.count=this.scroller.children.length,this.scroller.style.width=this.count+"00%",this.itemWidth=this.scroller.children[0].clientWidth,this.scrollWidth=this.itemWidth*this.count):this.scroller=this.wrapper.children[0];if(this.scrollerStyle=this.scroller.style,this.translateZ=d.hasPerspective&&this.options.HWCompositing?" translateZ(0)":"",this.options.useTransition=d.hasTransition&&this.options.useTransition,this.options.useTransform=d.hasTransform&&this.options.useTransform,this.options.eventPassthrough=this.options.eventPassthrough===!0?"vertical":this.options.eventPassthrough,this.options.preventDefault=!this.options.eventPassthrough&&this.options.preventDefault,this.options.scrollX="horizontal"==this.options.eventPassthrough?!1:this.options.scrollX,this.options.scrollY="vertical"==this.options.eventPassthrough?!1:this.options.scrollY,this.options.freeScroll=this.options.freeScroll&&!this.options.eventPassthrough,this.options.directionLockThreshold=this.options.eventPassthrough?0:this.options.directionLockThreshold,this.options.bounceEasing="string"==typeof this.options.bounceEasing?d.ease[this.options.bounceEasing]||d.ease.circular:this.options.bounceEasing,this.options.resizePolling=void 0===this.options.resizePolling?60:this.options.resizePolling,this.options.tap===!0&&(this.options.tap="tap"),this.options.useTransform===!1&&(this.scroller.style.position="relative"),this.x=0,this.y=0,this.directionX=0,this.directionY=0,this._events={},this._init(),this.refresh(),this.scrollTo(this.options.startX,this.options.startY),this.enable(),this.options.autoplay){var g=this;this.options.interval=this.options.interval||2e3,this.options.flag=setTimeout(function(){g._autoplay.apply(g)},g.options.interval)}}var c=window.requestAnimationFrame||window.webkitRequestAnimationFrame||window.mozRequestAnimationFrame||window.oRequestAnimationFrame||window.msRequestAnimationFrame||function(a){window.setTimeout(a,1e3/60)},d=function(){function a(a){return d===!1?!1:""===d?a:d+a.charAt(0).toUpperCase()+a.substr(1)}var b={},c=document.createElement("div").style,d=function(){for(var a,b=["t","webkitT","MozT","msT","OT"],d=0,e=b.length;e>d;d++)if(a=b[d]+"ransform",a in c)return b[d].substr(0,b[d].length-1);return!1}();b.getTime=Date.now||function(){return(new Date).getTime()},b.extend=function(a,b){for(var c in b)a[c]=b[c]},b.addEvent=function(a,b,c,d){a.addEventListener(b,c,!!d)},b.removeEvent=function(a,b,c,d){a.removeEventListener(b,c,!!d)},b.prefixPointerEvent=function(a){return window.MSPointerEvent?"MSPointer"+a.charAt(9).toUpperCase()+a.substr(10):a},b.momentum=function(a,b,c,d,e,f){var g,h,i=a-b,j=Math.abs(i)/c;return f=void 0===f?6e-4:f,g=a+j*j/(2*f)*(0>i?-1:1),h=j/f,d>g?(g=e?d-e/2.5*(j/8):d,i=Math.abs(g-a),h=i/j):g>0&&(g=e?e/2.5*(j/8):0,i=Math.abs(a)+g,h=i/j),{destination:Math.round(g),duration:h}};var e=a("transform");return b.extend(b,{hasTransform:e!==!1,hasPerspective:a("perspective")in c,hasTouch:"ontouchstart"in window,hasPointer:window.PointerEvent||window.MSPointerEvent,hasTransition:a("transition")in c}),b.isBadAndroid=/Android /.test(window.navigator.appVersion)&&!/Chrome\/\d/.test(window.navigator.appVersion),b.extend(b.style={},{transform:e,transitionTimingFunction:a("transitionTimingFunction"),transitionDuration:a("transitionDuration"),transitionDelay:a("transitionDelay"),transformOrigin:a("transformOrigin"),transitionProperty:a("transitionProperty")}),b.offset=function(a){for(var b=-a.offsetLeft,c=-a.offsetTop;a=a.offsetParent;)b-=a.offsetLeft,c-=a.offsetTop;return{left:b,top:c}},b.preventDefaultException=function(a,b){for(var c in b)if(b[c].test(a[c]))return!0;return!1},b.extend(b.eventType={},{touchstart:1,touchmove:1,touchend:1,mousedown:2,mousemove:2,mouseup:2,pointerdown:3,pointermove:3,pointerup:3,MSPointerDown:3,MSPointerMove:3,MSPointerUp:3}),b.extend(b.ease={},{quadratic:{style:"cubic-bezier(0.25, 0.46, 0.45, 0.94)",fn:function(a){return a*(2-a)}},circular:{style:"cubic-bezier(0.1, 0.57, 0.1, 1)",fn:function(a){return Math.sqrt(1- --a*a)}},back:{style:"cubic-bezier(0.175, 0.885, 0.32, 1.275)",fn:function(a){var b=4;return(a-=1)*a*((b+1)*a+b)+1}},bounce:{style:"",fn:function(a){return(a/=1)<1/2.75?7.5625*a*a:2/2.75>a?7.5625*(a-=1.5/2.75)*a+.75:2.5/2.75>a?7.5625*(a-=2.25/2.75)*a+.9375:7.5625*(a-=2.625/2.75)*a+.984375}},elastic:{style:"",fn:function(a){var b=.22,c=.4;return 0===a?0:1==a?1:c*Math.pow(2,-10*a)*Math.sin(2*(a-b/4)*Math.PI/b)+1}}}),b.tap=function(a,b){var c=document.createEvent("Event");c.initEvent(b,!0,!0),c.pageX=a.pageX,c.pageY=a.pageY,a.target.dispatchEvent(c)},b.click=function(a){var b,c=a.target;/(SELECT|INPUT|TEXTAREA)/i.test(c.tagName)||(b=document.createEvent("MouseEvents"),b.initMouseEvent("click",!0,!0,a.view,1,c.screenX,c.screenY,c.clientX,c.clientY,a.ctrlKey,a.altKey,a.shiftKey,a.metaKey,0,null),b._constructed=!0,c.dispatchEvent(b))},b}();b.prototype={_init:function(){this._initEvents()},_initEvents:function(a){var b=a?d.removeEvent:d.addEvent,c=this.options.bindToWrapper?this.wrapper:window;b(window,"orientationchange",this),b(window,"resize",this),this.options.click&&b(this.wrapper,"click",this,!0),this.options.disableMouse||(b(this.wrapper,"mousedown",this),b(c,"mousemove",this),b(c,"mousecancel",this),b(c,"mouseup",this)),d.hasPointer&&!this.options.disablePointer&&(b(this.wrapper,d.prefixPointerEvent("pointerdown"),this),b(c,d.prefixPointerEvent("pointermove"),this),b(c,d.prefixPointerEvent("pointercancel"),this),b(c,d.prefixPointerEvent("pointerup"),this)),d.hasTouch&&!this.options.disableTouch&&(b(this.wrapper,"touchstart",this),b(c,"touchmove",this),b(c,"touchcancel",this),b(c,"touchend",this)),b(this.scroller,"transitionend",this),b(this.scroller,"webkitTransitionEnd",this),b(this.scroller,"oTransitionEnd",this),b(this.scroller,"MSTransitionEnd",this),"tab"===this.options.role&&(b(this.nav,"touchend",this),b(this.nav,"mouseup",this),b(this.nav,"pointerup",this))},refresh:function(){this.wrapper.offsetHeight;this.wrapperWidth=this.wrapper.clientWidth,this.wrapperHeight=this.wrapper.clientHeight;var a=window.getComputedStyle(this.wrapper,null),b=a["padding-top"].replace(/[^-\d.]/g,""),c=a["padding-bottom"].replace(/[^-\d.]/g,""),e=a["padding-left"].replace(/[^-\d.]/g,""),f=a["padding-right"].replace(/[^-\d.]/g,""),g=window.getComputedStyle(this.scroller,null),h=g["margin-top"].replace(/[^-\d.]/g,""),i=g["margin-bottom"].replace(/[^-\d.]/g,""),j=g["margin-left"].replace(/[^-\d.]/g,""),k=g["margin-right"].replace(/[^-\d.]/g,"");this.scrollerWidth=this.scroller.offsetWidth+parseInt(e)+parseInt(f)+parseInt(j)+parseInt(k),this.scrollerHeight=this.scroller.offsetHeight+parseInt(b)+parseInt(c)+parseInt(h)+parseInt(i),("slider"===this.options.role||"tab"===this.options.role)&&(this.itemWidth=this.scroller.children[0].clientWidth,this.scrollWidth=this.itemWidth*this.count,this.scrollerWidth=this.scrollWidth),this.maxScrollX=this.wrapperWidth-this.scrollerWidth,this.maxScrollY=this.wrapperHeight-this.scrollerHeight,this.hasHorizontalScroll=this.options.scrollX&&this.maxScrollX<0,this.hasVerticalScroll=this.options.scrollY&&this.maxScrollY<0,this.hasHorizontalScroll||(this.maxScrollX=0,this.scrollerWidth=this.wrapperWidth),this.hasVerticalScroll||(this.maxScrollY=0,this.scrollerHeight=this.wrapperHeight),this.endTime=0,this.directionX=0,this.directionY=0,this.wrapperOffset=d.offset(this.wrapper),this.resetPosition()},handleEvent:function(a){switch(a.type){case"touchstart":case"pointerdown":case"MSPointerDown":case"mousedown":this._start(a);break;case"touchmove":case"pointermove":case"MSPointerMove":case"mousemove":this._move(a);break;case"touchend":case"pointerup":case"MSPointerUp":case"mouseup":case"touchcancel":case"pointercancel":case"MSPointerCancel":case"mousecancel":this._end(a);break;case"orientationchange":case"resize":this._resize();break;case"transitionend":case"webkitTransitionEnd":case"oTransitionEnd":case"MSTransitionEnd":this._transitionEnd(a);break;case"wheel":case"DOMMouseScroll":case"mousewheel":this._wheel(a);break;case"keydown":this._key(a);break;case"click":a._constructed||(a.preventDefault(),a.stopPropagation())}},_start:function(a){if(!(1!=d.eventType[a.type]&&0!==a.button||!this.enabled||this.initiated&&d.eventType[a.type]!==this.initiated)){!this.options.preventDefault||d.isBadAndroid||d.preventDefaultException(a.target,this.options.preventDefaultException)||a.preventDefault();var b,c=a.touches?a.touches[0]:a;if(this.initiated=d.eventType[a.type],this.moved=!1,this.distX=0,this.distY=0,this.directionX=0,this.directionY=0,this.directionLocked=0,this._transitionTime(),this.startTime=d.getTime(),this.options.useTransition&&this.isInTransition&&"slider"!==this.options.role&&"tab"!==this.options.role?(this.isInTransition=!1,b=this.getComputedPosition(),this._translate(Math.round(b.x),Math.round(b.y))):!this.options.useTransition&&this.isAnimating&&(this.isAnimating=!1),this.startX=this.x,this.startY=this.y,this.absStartX=this.x,this.absStartY=this.y,this.pointX=c.pageX,this.pointY=c.pageY,this.options.autoplay){var e=this;clearTimeout(this.options.flag),this.options.flag=setTimeout(function(){e._autoplay.apply(e)},e.options.interval)}event.stopPropagation()}},_move:function(b){if(this.enabled&&d.eventType[b.type]===this.initiated){this.options.preventDefault&&b.preventDefault();var c,e,f,g,h=b.touches?b.touches[0]:b,i=h.pageX-this.pointX,j=h.pageY-this.pointY,k=d.getTime();if(this.pointX=h.pageX,this.pointY=h.pageY,this.distX+=i,this.distY+=j,f=Math.abs(this.distX),g=Math.abs(this.distY),!(k-this.endTime>300&&10>f&&10>g)){if(this.directionLocked||this.options.freeScroll||(this.directionLocked=f>g+this.options.directionLockThreshold?"h":g>=f+this.options.directionLockThreshold?"v":"n"),"h"==this.directionLocked){if("tab"===this.options.role&&a(this.scroller).children("li").height("auto"),"vertical"==this.options.eventPassthrough)b.preventDefault();else if("horizontal"==this.options.eventPassthrough)return void(this.initiated=!1);j=0}else if("v"==this.directionLocked){if("horizontal"==this.options.eventPassthrough)b.preventDefault();else if("vertical"==this.options.eventPassthrough)return void(this.initiated=!1);i=0}i=this.hasHorizontalScroll?i:0,j=this.hasVerticalScroll?j:0,c=this.x+i,e=this.y+j,(c>0||c<this.maxScrollX)&&(c=this.options.bounce?this.x+i/3:c>0?0:this.maxScrollX),(e>0||e<this.maxScrollY)&&(e=this.options.bounce?this.y+j/3:e>0?0:this.maxScrollY),this.directionX=i>0?-1:0>i?1:0,this.directionY=j>0?-1:0>j?1:0,this.moved=!0,this._translate(c,e),k-this.startTime>300&&(this.startTime=k,this.startX=this.x,this.startY=this.y)}}},_end:function(b){if(this.enabled&&d.eventType[b.type]===this.initiated){this.options.preventDefault&&!d.preventDefaultException(b.target,this.options.preventDefaultException)&&b.preventDefault();var c,e,f=(b.changedTouches?b.changedTouches[0]:b,d.getTime()-this.startTime),g=Math.round(this.x),h=Math.round(this.y),i=Math.abs(g-this.startX),j=(Math.abs(h-this.startY),0),k="";if(this.isInTransition=0,this.initiated=0,this.endTime=d.getTime(),this.resetPosition(this.options.bounceTime))return void("tab"===this.options.role&&a(this.scroller.children[this.currentPage]).siblings("li").height(0));if(this.scrollTo(g,h),this.moved||(this.options.tap&&1===d.eventType[b.type]&&d.tap(b,this.options.tap),this.options.click&&d.click(b)),this.options.momentum&&300>f&&(c=this.hasHorizontalScroll?d.momentum(this.x,this.startX,f,this.maxScrollX,this.options.bounce?this.wrapperWidth:0,this.options.deceleration):{destination:g,duration:0},e=this.hasVerticalScroll?d.momentum(this.y,this.startY,f,this.maxScrollY,this.options.bounce?this.wrapperHeight:0,this.options.deceleration):{destination:h,duration:0},g=c.destination,h=e.destination,j=Math.max(c.duration,e.duration),this.isInTransition=1),g!=this.x||h!=this.y)return(g>0||g<this.maxScrollX||h>0||h<this.maxScrollY)&&(k=d.ease.quadratic),void this.scrollTo(g,h,j,k);if("tab"===this.options.role&&a(event.target).closest("ul").hasClass("ui-tab-nav")){a(this.nav).children().removeClass("current"),a(event.target).addClass("current");var l=this.currentPage;this.currentPage=a(event.target).index(),a(this.scroller).children().height("auto"),this._execEvent("beforeScrollStart",l,this.currentPage)}("slider"===this.options.role||"tab"===this.options.role)&&(30>i?this.scrollTo(-this.itemWidth*this.currentPage,0,this.options.bounceTime,this.options.bounceEasing):g-this.startX<0?(this._execEvent("beforeScrollStart",this.currentPage,this.currentPage+1),this.scrollTo(-this.itemWidth*++this.currentPage,0,this.options.bounceTime,this.options.bounceEasing)):g-this.startX>=0&&(this._execEvent("beforeScrollStart",this.currentPage,this.currentPage-1),this.scrollTo(-this.itemWidth*--this.currentPage,0,this.options.bounceTime,this.options.bounceEasing)),"tab"===this.options.role&&a(this.scroller.children[this.currentPage]).siblings("li").height(0),this.indicator&&i>=30?(a(this.indicator).children().removeClass("current"),a(this.indicator.children[this.currentPage]).addClass("current")):this.nav&&i>=30&&(a(this.nav).children().removeClass("current"),a(this.nav.children[this.currentPage]).addClass("current")),a(this.scroller).children().removeClass("current"),a(this.scroller.children[this.currentPage]).addClass("current"))}},_resize:function(){var a=this;clearTimeout(this.resizeTimeout),this.resizeTimeout=setTimeout(function(){a.refresh()},this.options.resizePolling)},_transitionEnd:function(a){a.target==this.scroller&&this.isInTransition&&(this._transitionTime(),this.resetPosition(this.options.bounceTime)||(this.isInTransition=!1,this._execEvent("scrollEnd",this.currentPage)))},destroy:function(){this._initEvents(!0)},resetPosition:function(a){var b=this.x,c=this.y;return a=a||0,!this.hasHorizontalScroll||this.x>0?b=0:this.x<this.maxScrollX&&(b=this.maxScrollX),!this.hasVerticalScroll||this.y>0?c=0:this.y<this.maxScrollY&&(c=this.maxScrollY),b==this.x&&c==this.y?!1:(this.scrollTo(b,c,a,this.options.bounceEasing),!0)},disable:function(){this.enabled=!1},enable:function(){this.enabled=!0},on:function(a,b){this._events[a]||(this._events[a]=[]),this._events[a].push(b)},off:function(a,b){if(this._events[a]){var c=this._events[a].indexOf(b);c>-1&&this._events[a].splice(c,1)}},_execEvent:function(a){if(this._events[a]){var b=0,c=this._events[a].length;if(c)for(;c>b;b++)this._events[a][b].apply(this,[].slice.call(arguments,1))}},scrollTo:function(a,b,c,e){e=e||d.ease.circular,this.isInTransition=this.options.useTransition&&c>0,!c||this.options.useTransition&&e.style?(("slider"===this.options.role||"tab"===this.options.role)&&(c=this.options.duration,this.scrollerStyle[d.style.transitionProperty]=d.style.transform),this.scrollerStyle[d.style.transitionTimingFunction]=e.style,this._transitionTime(c),this._translate(a,b)):this._animate(a,b,c,e.fn)},scrollToElement:function(a,b,c,e,f){if(a=a.nodeType?a:this.scroller.querySelector(a)){var g=d.offset(a);g.left-=this.wrapperOffset.left,g.top-=this.wrapperOffset.top,c===!0&&(c=Math.round(a.offsetWidth/2-this.wrapper.offsetWidth/2)),e===!0&&(e=Math.round(a.offsetHeight/2-this.wrapper.offsetHeight/2)),g.left-=c||0,g.top-=e||0,g.left=g.left>0?0:g.left<this.maxScrollX?this.maxScrollX:g.left,g.top=g.top>0?0:g.top<this.maxScrollY?this.maxScrollY:g.top,b=void 0===b||null===b||"auto"===b?Math.max(Math.abs(this.x-g.left),Math.abs(this.y-g.top)):b,this.scrollTo(g.left,g.top,b,f)}},_transitionTime:function(a){a=a||0,this.scrollerStyle[d.style.transitionDuration]=a+"ms",!a&&d.isBadAndroid&&(this.scrollerStyle[d.style.transitionDuration]="0.001s")},_translate:function(a,b){this.options.useTransform?this.scrollerStyle[d.style.transform]="translate("+a+"px,"+b+"px)"+this.translateZ:(a=Math.round(a),b=Math.round(b),this.scrollerStyle.left=a+"px",this.scrollerStyle.top=b+"px"),this.x=a,this.y=b},getComputedPosition:function(){var a,b,c=window.getComputedStyle(this.scroller,null);return this.options.useTransform?(c=c[d.style.transform].split(")")[0].split(", "),a=+(c[12]||c[4]),b=+(c[13]||c[5])):(a=+c.left.replace(/[^-\d.]/g,""),b=+c.top.replace(/[^-\d.]/g,"")),{x:a,y:b}},_animate:function(a,b,e,f){function g(){var m,n,o,p=d.getTime();return p>=l?(h.isAnimating=!1,h._translate(a,b),void(h.resetPosition(h.options.bounceTime)||h._execEvent("scrollEnd",this.currentPage))):(p=(p-k)/e,o=f(p),m=(a-i)*o+i,n=(b-j)*o+j,h._translate(m,n),void(h.isAnimating&&c(g)))}var h=this,i=this.x,j=this.y,k=d.getTime(),l=k+e;this.isAnimating=!0,g()},_autoplay:function(){var b=this,c=b.currentPage;b.currentPage=b.currentPage>=b.count-1?0:++b.currentPage,b._execEvent("beforeScrollStart",c,b.currentPage),"tab"===this.options.role&&(a(this.scroller).children().height("auto"),document.body.scrollTop=0),b.scrollTo(-b.itemWidth*b.currentPage,0,b.options.bounceTime,b.options.bounceEasing),b.indicator?(a(b.indicator).children().removeClass("current"),a(b.indicator.children[b.currentPage]).addClass("current"),a(b.scroller).children().removeClass("current"),a(b.scroller.children[b.currentPage]).addClass("current")):b.nav&&(a(b.nav).children().removeClass("current"),a(b.nav.children[b.currentPage]).addClass("current"),a(b.scroller).children().removeClass("current"),a(b.scroller.children[b.currentPage]).addClass("current")),b.options.flag=setTimeout(function(){b._autoplay.apply(b)},b.options.interval)}},window.fz=window.fz||{},window.frozen=window.frozen||{},window.fz.Scroll=window.frozen.Scroll=b,"function"==typeof define&&define(function(a,c,d){d.exports=b})}(window.Zepto);!function(a){function b(){return"#"+("00000"+(16777216*Math.random()<<0).toString(16)).slice(-6)}function c(){return[20*Math.random()-10,20*Math.random()-10]}function d(a){var b=a.css("position");return"relative"===b||"fixed"===b||"absolute"===b?!0:!1}function e(a){a.preventDefault()}function f(b){var c=this;return a.isArray(c)&&a(this).length||(c=a("body")),c.each(function(){var d=a(this),e=d.data("fz.cover");e||d.data("fz.cover",e=new h(this,a.extend({},g,"object"==typeof b&&b,d.data()))),"string"==typeof b&&e[b].call(c)})}var g={trigger:null,dismiss:null,duration:1e3,startPos:"source",offset:[0,0],expandAxis:"y",isFloat:!0,zIndex:999,callback:function(){}},h=function(b,c){this.element=a(b),this.trigger=a(c.trigger),this.dismiss=a(c.dismiss),this.option=c,this.initMask(),this.render(),this._isShown=!1,this._isDismiss=!1,this._bindTrigger(),this.position||(this.position={},this.position.screenWidth=document.documentElement.clientWidth,this.position.screenHeight=document.documentElement.clientHeight)};h.prototype={initMask:function(){this._mask=a("<div></div>"),this._mask.css({position:"absolute",top:0,left:0,width:100,height:100,"z-index":-1,"-webkit-transform":"scale(0)"})},render:function(){this._mask.appendTo(this.element),d(this.element)||this.element.css({position:"relative",overflow:"hidden"})},setConfig:function(b){return this._isShown||(b=a.extend(b,this.currentTrigger.data()),this.currentOption=b),this},show:function(a){var d=this,e=this.currentOption;if(!this._isShown&&(this.currentTrigger||a)){a&&(this.currentTrigger=a),e.callback("show",d),this._preventDefault(),d._isDismiss=!1,this._isShown=!0;var f=c(),g=2;e.isFloat&&(this._floatTrigger(),this.currentTrigger.css({zIndex:e.zIndex+1}));var h=this._getMaskPos(e.startPos,e.expandAxis),i="x"==e.expandAxis?"scale(0,1)":"y"==e.expandAxis?"scale(1,0)":"scale(0,0)";this._mask.css({left:h[0]+e.offset[0],top:h[1]+e.offset[1],background:e.background?e.background:b(),"z-index":e.zIndex,"-webkit-transform":i+" skew("+f[0]+"deg,"+f[1]+"deg)"}),this._aniMask(e.duration,e.offset,g)}return this},hide:function(){var a=this;a.option.callback("hide",a),this._isShown&&(this._isDismiss=!0,this._mask.css({"-webkit-transform":this._originTransform}))},hidden:function(){this._isShown=!1,this._isDismiss=!1,this._mask.css({"-webkit-transition":"none"}),this._relaseDefault(),this.currentTrigger.css(this._triggerOriginCss)},_bindTrigger:function(){var b=this;this.trigger.on("tap",function(){return b._isShown||(b.currentTrigger=a(this)),b.setConfig(a.extend({},a(this).data(),b.option)).show(a(this)),!1}),this.dismiss.on("tap",function(){return b.dismiss=a(this),b.hide(),!1}),this._mask[0].addEventListener("webkitTransitionEnd",function(){b._isShown&&!b._isDismiss?b.option.callback("shown",b):(b.hidden(),b.option.callback("hidden",b))},!1)},_preventDefault:function(){document.addEventListener("mousewheel",e,!1),document.body.addEventListener("touchmove",e,!1),document.documentElement.addEventListener("touchmove",e,!1)},_relaseDefault:function(){document.removeEventListener("mousewheel",e,!1),document.body.removeEventListener("touchmove",e,!1),document.documentElement.removeEventListener("touchmove",e,!1)},_floatTrigger:function(){this._triggerOriginCss={position:this.currentTrigger.css("position"),"z-index":this.currentTrigger.css("z-index")},d(this.currentTrigger)||this.currentTrigger.css({position:"relative"})},_getMaskPos:function(a,b){this.position||(this.position={},this.position.screenWidth=document.documentElement.clientWidth,this.position.screenHeight=document.documentElement.clientHeight),"x"==b?this._mask.css({height:this.position.screenHeight}):"y"==b&&this._mask.css({width:this.position.screenWidth}),this.position.scrollTop=document.body.scrollTop,this.position.scrollLeft=document.body.scrollLeft,this.position.offsetTop=this.element.offset().top,this.position.offsetLeft=this.element.offset().left,this.position.triggerLeft=this.currentTrigger.offset().left,this.position.triggerTop=this.currentTrigger.offset().top,this.position.triggerHeight=this.currentTrigger.height(),this.position.triggerWidth=this.currentTrigger.width();var c=parseInt(this._mask.css("width")),d=parseInt(this._mask.css("height")),e=this.position.scrollLeft-this.position.offsetLeft+this.position.screenWidth/2-c/2,f=0;return"top"==a?f=this.position.scrollTop-this.position.offsetTop-d/2:"bottom"==a?f=this.position.scrollTop-this.position.offsetTop+this.position.screenHeight-d/2:"center"==a?f=this.position.scrollTop-this.position.offsetTop+this.position.screenHeight/2-d/2:(e=this.position.triggerLeft-this.position.offsetLeft+this.position.triggerWidth/2-c/2,f=this.position.triggerTop-this.position.offsetTop+this.position.triggerHeight/2-d/2),[e,f]},_aniMask:function(a,b,c){var d=this;this._originTransform=this._mask.css("-webkit-transform");var e=Math.ceil(this.position.screenWidth/parseInt(this._mask.css("width")))*c,f=Math.ceil(this.position.screenHeight/parseInt(this._mask.css("height")))*c;setTimeout(function(){d._mask.css({"-webkit-transition":"all "+a+"ms","-webkit-transform":"scale("+e+","+f+") skew(0deg,0deg)"})},200)}},a.fn.cover=a.cover=f}(window.Zepto);if("undefined"==typeof Zepto)throw new Error("Parallax.js's script requires Zepto");!function(a){"use strict";function b(){w.loading?a(".wrapper").append('<div class="parallax-loading"><i class="ui-loading ui-loading-white"></i></div>'):y=!1,p=0,o="stay",q=u.length,r=a(window).width(),s=a(window).height(),v=a("[data-animation]");for(var b=0;q>b;b++)a(u[b]).attr("data-id",b+1);t.addClass(w.direction).addClass(w.swipeAnim),u.css({width:r+"px",height:s+"px"}),"horizontal"===w.direction?t.css("width",r*u.length):t.css("height",s*u.length),"cover"===w.swipeAnim&&(t.css({width:r,height:s}),u[0].style.display="block"),w.loading||(a(u[p]).addClass("current"),w.onchange(p,u[p],o),j())}function c(a){return y===!0?(event.preventDefault(),!1):(x=!0,k="horizontal"===w.direction?a.pageX:a.pageY,"default"===w.swipeAnim&&(t.addClass("drag"),n=t.css("-webkit-transform").replace("matrix(","").replace(")","").split(","),n=parseInt("horizontal"===w.direction?n[4]:n[5])),"cover"===w.swipeAnim&&w.drag&&u.addClass("drag"),void(m=1))}function d(a){return y===!0||x===!1?(event.preventDefault(),!1):(event.preventDefault(),l="horizontal"===w.direction?a.pageX:a.pageY,h(),w.drag&&!i()&&f(),void(m=2))}function e(b){y===!0||2!==m||(x=!1,l="horizontal"===w.direction?b.pageX:b.pageY,"default"!==w.swipeAnim||i()?"cover"!==w.swipeAnim||i()||(Math.abs(l-k)<=50&&l>=k&&w.drag?(g(p,"keep-backward"),o="stay"):Math.abs(l-k)<=50&&k>l&&w.drag?(g(p,"keep-forward"),o="stay"):Math.abs(l-k)>50&&l>=k&&w.drag?(g(p-1,"backward"),o="backward"):Math.abs(l-k)>50&&k>l&&w.drag?(g(p+1,"forward"),o="forward"):Math.abs(l-k)>50&&l>=k&&!w.drag?(g(p-1,"backward"),o="backward"):Math.abs(l-k)>50&&k>l&&!w.drag&&(g(p+1,"forward"),o="forward")):(t.removeClass("drag"),Math.abs(l-k)<=50?(g(p),o="stay"):l>=k?(g(p-1),o="backward"):k>l&&(g(p+1),o="forward")),w.indicator&&a(a(".parallax-h-indicator ul li, .parallax-v-indicator ul li").removeClass("current").get(p)).addClass("current"),m=3)}function f(){if("default"===w.swipeAnim){var b=n+l-k;"horizontal"===w.direction?t.css("-webkit-transform","matrix(1, 0, 0, 1, "+b+", 0)"):t.css("-webkit-transform","matrix(1, 0, 0, 1, 0, "+b+")")}else if("cover"===w.swipeAnim){var b=l-k,c=a(u[p-1]),d=a(u[p+1]);a(u).css({"z-index":0}),"horizontal"===w.direction&&l>=k?c.css({"z-index":2,display:"block","-webkit-transform":"translateX("+(b-r)+"px)"}):"horizontal"===w.direction&&k>l?d.css({"z-index":2,display:"block","-webkit-transform":"translateX("+(r+b)+"px)"}):"vertical"===w.direction&&l>=k?c.css({"z-index":2,display:"block","-webkit-transform":"translateY("+(b-s)+"px)"}):"vertical"===w.direction&&k>l&&d.css({"z-index":2,display:"block","-webkit-transform":"translateY("+(s+b)+"px)"})}}function g(b,c){if(p=b,"default"===w.swipeAnim){var d=0;d="horizontal"===w.direction?b*-r:b*-s,t.css("horizontal"===w.direction?{"-webkit-transform":"matrix(1, 0, 0, 1, "+d+", 0)"}:{"-webkit-transform":"matrix(1, 0, 0, 1, 0, "+d+")"})}else"cover"===w.swipeAnim&&("keep-backward"===c&&w.drag?(u.removeClass("drag"),a(u[p-1]).css("horizontal"===w.direction?{"-webkit-transform":"translateX(-100%)"}:{"-webkit-transform":"translateY(-100%)"})):"keep-forward"===c&&w.drag?(u.removeClass("drag"),a(u[p+1]).css("horizontal"===w.direction?{"-webkit-transform":"translateX(100%)"}:{"-webkit-transform":"translateY(100%)"})):"forward"===c&&w.drag?(u.removeClass("drag"),a(u[p-1]).addClass("back"),u[p].style.webkitTransform="translate(0, 0)"):"backward"===c&&w.drag?(u.removeClass("drag"),a(u[p+1]).addClass("back"),u[p].style.webkitTransform="translate(0, 0)"):"forward"!==c||w.drag?"backward"!==c||w.drag||(t.addClass("animate"),a(u[p+1]).addClass("back"),a(u[p]).addClass("front").show()):(t.addClass("animate"),a(u[p-1]).addClass("back"),a(u[p]).addClass("front").show()));y=!0,setTimeout(function(){y=!1},300)}function h(){"horizontal"===w.direction?l>=k?t.removeClass("forward").addClass("backward"):k>l&&t.removeClass("backward").addClass("forward"):l>=k?t.removeClass("forward").addClass("backward"):k>l&&t.removeClass("backward").addClass("forward")}function i(){if("horizontal"===w.direction){if(l>=k&&0===p||k>=l&&p===q-1)return!0}else if(l>=k&&0===p||k>=l&&p===q-1)return!0;return!1}function j(){v.css({"-webkit-animation":"none"}),a(".current [data-animation]").each(function(b,c){var d=a(c),e=d.attr("data-animation"),f=d.attr("data-duration")||500,g=d.attr("data-timing-function")||"ease",h=d.attr("data-delay")?d.attr("data-delay"):0;"followSlide"===e&&("horizontal"===w.direction&&"forward"===o?e="followSlideToLeft":"horizontal"===w.direction&&"backward"===o?e="followSlideToRight":"vertical"===w.direction&&"forward"===o?e="followSlideToTop":"vertical"===w.direction&&"backward"===o&&(e="followSlideToBottom")),d.css({"-webkit-animation":e+" "+f+"ms "+g+" "+h+"ms both",animation:e+" "+f+"ms "+g+" "+h+"ms both"})})}var k,l,m,n,o,p,q,r,s,t,u,v,w,x=!1,y=!0;a.fn.parallax=function(c){return w=a.extend({},a.fn.parallax.defaults,c),this.each(function(){t=a(this),u=t.find(".page"),b()})},a.fn.parallax.defaults={direction:"vertical",swipeAnim:"default",drag:!0,loading:!1,indicator:!1,arrow:!1,onchange:function(){},orientationchange:function(){}},a(document).on("touchstart",".page",function(a){c(a.changedTouches[0])}).on("touchmove",".page",function(a){d(a.changedTouches[0])}).on("touchend",".page",function(a){e(a.changedTouches[0])}).on("webkitAnimationEnd webkitTransitionEnd",".pages",function(){"stay"!==o&&(setTimeout(function(){a(".back").hide().removeClass("back"),a(".front").show().removeClass("front"),t.removeClass("forward backward animate")},10),a(u.removeClass("current").get(p)).addClass("current"),w.onchange(p,u[p],o),j())}),a(".page *").on("webkitAnimationEnd",function(){event.stopPropagation()}),a(window).on("load",function(){if(w.loading&&(a(".parallax-loading").remove(),y=!1,a(u[p]).addClass("current"),w.onchange(p,u[p],o),j()),w.indicator){y=!1;var b="horizontal"===w.direction?"parallax-h-indicator":"parallax-v-indicator";a(".wrapper").append("<div class="+b+"></div>");for(var c="<ul>",d=1;q>=d;d++)c+=1===d?'<li class="current">'+d+"</li>":"<li>"+d+"</li>";c+="</ul>",a("."+b).append(c)}w.arrow&&(u.append('<span class="parallax-arrow"></span>'),a(u[q-1]).find(".parallax-arrow").remove())}),window.addEventListener("onorientationchange"in window?"orientationchange":"resize",function(){(180===window.orientation||0===window.orientation)&&w.orientationchange("portrait"),(90===window.orientation||-90===window.orientation)&&w.orientationchange("landscape")},!1)}(Zepto);