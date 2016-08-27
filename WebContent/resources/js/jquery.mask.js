(function($) {

	$.extend({
		uuid : function() {
			var seed = 0;
			return function() {
				return seed++;
			}
		}()
	});

	$.fn.extend({
		// 展示遮罩
		mask : function(msg) {
			this.unmask();

			// 创建一个遮罩层
			var maskDiv = $('<div class="mask" />');
			maskDiv.prependTo(this);

			// 创建一个提示信息层
			if (msg) {
				maskDiv.append('<div>' + msg + '</div>');
			}

			// 页面大小改变后，也改变遮罩层相关的大小和提示信息层的位置
			var resizeEvent = "resize." + $.uuid();
			var resizeHandler = $.proxy(this._resizeMask, this);
			$(window).bind(resizeEvent, resizeHandler).trigger(resizeEvent);
			this.data("resizeEvent", resizeEvent);

			this.css("overflow", "hidden");

			return this;
		},

		_resizeMask : function() {
			var position = this.position();
			var width = this.outerWidth();
			var height = this.outerHeight();
			
			if (this[0] == document.body) {
				width = Math.max(width, $(window).outerWidth());
				height = Math.max(height, $(window).outerHeight());
			}

			var maskDiv = this.find("> div.mask");
			maskDiv.css({
				width : width,
				height : height,
				top : position.top,
				left : position.left
			});

			var msgDiv = maskDiv.find("div");
			if (msgDiv.length != 0) {
				var left = $(window).scrollLeft()
						+ (maskDiv.outerWidth() - msgDiv.outerWidth()) / 2;
				
				if (this[0] == document.body) { 
					var top = $(window).scrollTop() + $(window).outerHeight() / 2;
				} else {
					var top = $(window).scrollTop() + (maskDiv.outerHeight() - msgDiv.outerHeight()) / 2;
				}
			
				msgDiv.css({
					top : top,
					left : left
				});
			}
		},

		unmask : function() {
			if (this.isMasked()) {
				this.find("> div.mask:first").remove();

				var resizeEvent = this.data("resizeEvent");
				this.removeData("resizeEvent");
				$(window).off(resizeEvent);

				this.css("overflow", "visible");
			}
		},

		isMasked : function() {
			return (this.find("> div.mask:first").length != 0);
		},

		toggleMask : function(msg) {
			if (this.isMasked()) {
				this.unmask();
			} else {
				this.mask(msg);
			}
		}
	});
})(jQuery)
