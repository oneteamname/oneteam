$.fn.autoSlide=function(options){var ths_ul=$(this).find("ul");var ths_li=ths_ul.find("li");var s_temp=0;var s_l=ths_li.length;var op={time:4000};op=$.extend({},op,options);function countWid(){var item=0;ths_li.each(function(i,n){item+=parseInt($(n).width());});ths_ul.css({"width":item*2});ths_li.clone().appendTo(ths_ul);}
countWid();function autoslide(){var this_arg=arguments;var self=this;var s_left=parseInt(ths_ul.css("left"));if(s_temp<(s_l+1)){ths_ul.animate({"left":(s_left-parseInt(ths_li.eq(s_temp).width()))},1000,function(){if(s_temp==s_l){s_temp=0;ths_ul.css({"left":0});}});s_temp++;};setTimeout(function(){this_arg.callee(self);},op.time);}
setTimeout(function(){autoslide();},op.time);}