var Resize = Class.create();
Resize.prototype = {
  initialize: function(obj, options) {
	this._obj = $(obj);	
	this._styleWidth = this._styleHeight = this._styleLeft = this._styleTop = 0;
	this._sideRight = this._sideDown = this._sideLeft = this._sideUp = 0;
	this._fixLeft = this._fixTop = 0;
	this._scaleLeft = this._scaleTop = 0;	
	this._mxSet = function(){};
	this._mxRightWidth = this._mxDownHeight = this._mxUpHeight = this._mxLeftWidth = 0;
	this._mxScaleWidth = this._mxScaleHeight = 0;
	this._fun = function(){};
	var _style = CurrentStyle(this._obj);
	this._borderX = (parseInt(_style.borderLeftWidth) || 0) + (parseInt(_style.borderRightWidth) || 0);
	this._borderY = (parseInt(_style.borderTopWidth) || 0) + (parseInt(_style.borderBottomWidth) || 0);
	this._fR = BindAsEventListener(this, this.Resize);
	this._fS = Bind(this, this.Stop);	
	this.SetOptions(options);
	this.Max = !!this.options.Max;
	this._mxContainer = $(this.options.mxContainer) || null;
	this.mxLeft = Math.round(this.options.mxLeft);
	this.mxRight = Math.round(this.options.mxRight);
	this.mxTop = Math.round(this.options.mxTop);
	this.mxBottom = Math.round(this.options.mxBottom);
	this.Min = !!this.options.Min;
	this.minWidth = Math.round(this.options.minWidth);
	this.minHeight = Math.round(this.options.minHeight);
	this.Scale = !!this.options.Scale;
	this.Ratio = Math.max(this.options.Ratio, 0);	
	this.onResize = this.options.onResize;	
	this._obj.style.position = "absolute";
	!this._mxContainer || CurrentStyle(this._mxContainer).position == "relative" || (this._mxContainer.style.position = "relative");
  },
  SetOptions: function(options) {
    this.options = {
		Max:		false,
		mxContainer:"",
		mxLeft:		0,
		mxRight:	9999,
		mxTop:		0,
		mxBottom:	9999,
		Min:		false,
		minWidth:	50,
		minHeight:	50,
		Scale:		false,
		Ratio:		0,
		onResize:	function(){}
    };
    Extend(this.options, options || {});
  },
  Set: function(resize, side) {
	var resize = $(resize), fun;
	if(!resize) return;
	switch (side.toLowerCase()) {
	case "up" :
		fun = this.Up;
		break;
	case "down" :
		fun = this.Down;
		break;
	case "left" :
		fun = this.Left;
		break;
	case "right" :
		fun = this.Right;
		break;
	case "left-up" :
		fun = this.LeftUp;
		break;
	case "right-up" :
		fun = this.RightUp;
		break;
	case "left-down" :
		fun = this.LeftDown;
		break;
	case "right-down" :
	default :
		fun = this.RightDown;
	};
	addEventHandler(resize, "mousedown", BindAsEventListener(this, this.Start, fun));
  },
  Start: function(e, fun, touch) {	
	e.stopPropagation ? e.stopPropagation() : (e.cancelBubble = true);
	this._fun = fun;
	this._styleWidth = this._obj.clientWidth;
	this._styleHeight = this._obj.clientHeight;
	this._styleLeft = this._obj.offsetLeft;
	this._styleTop = this._obj.offsetTop;
	this._sideLeft = e.clientX - this._styleWidth;
	this._sideRight = e.clientX + this._styleWidth;
	this._sideUp = e.clientY - this._styleHeight;
	this._sideDown = e.clientY + this._styleHeight;
	this._fixLeft = this._styleLeft + this._styleWidth;
	this._fixTop = this._styleTop + this._styleHeight;
	if(this.Scale){
		this.Ratio = Math.max(this.Ratio, 0) || this._styleWidth / this._styleHeight;
		this._scaleLeft = this._styleLeft + this._styleWidth / 2;
		this._scaleTop = this._styleTop + this._styleHeight / 2;
	};
	if(this.Max){
		var mxLeft = this.mxLeft, mxRight = this.mxRight, mxTop = this.mxTop, mxBottom = this.mxBottom;
		if(!!this._mxContainer){
			mxLeft = Math.max(mxLeft, 0);
			mxTop = Math.max(mxTop, 0);
			mxRight = Math.min(mxRight, this._mxContainer.clientWidth);
			mxBottom = Math.min(mxBottom, this._mxContainer.clientHeight);
		};
		mxRight = Math.max(mxRight, mxLeft + (this.Min ? this.minWidth : 0) + this._borderX);
		mxBottom = Math.max(mxBottom, mxTop + (this.Min ? this.minHeight : 0) + this._borderY);
		this._mxSet = function(){
			this._mxRightWidth = mxRight - this._styleLeft - this._borderX;
			this._mxDownHeight = mxBottom - this._styleTop - this._borderY;
			this._mxUpHeight = Math.max(this._fixTop - mxTop, this.Min ? this.minHeight : 0);
			this._mxLeftWidth = Math.max(this._fixLeft - mxLeft, this.Min ? this.minWidth : 0);
		};
		this._mxSet();
		if(this.Scale){
			this._mxScaleWidth = Math.min(this._scaleLeft - mxLeft, mxRight - this._scaleLeft - this._borderX) * 2;
			this._mxScaleHeight = Math.min(this._scaleTop - mxTop, mxBottom - this._scaleTop - this._borderY) * 2;
		};
	};
	addEventHandler(document, "mousemove", this._fR);
	addEventHandler(document, "mouseup", this._fS);
	if(isIE){
		addEventHandler(this._obj, "losecapture", this._fS);
		this._obj.setCapture();
	}else{
		addEventHandler(window, "blur", this._fS);
		e.preventDefault();
	};
  },
  Resize: function(e) {
	window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();
	this._fun(e);
	with(this._obj.style){
		width = this._styleWidth + "px"; height = this._styleHeight + "px";
		top = this._styleTop + "px"; left = this._styleLeft + "px";
	}
	this.onResize();
  },
  Up: function(e) {
	this.RepairY(this._sideDown - e.clientY, this._mxUpHeight);
	this.RepairTop();
	this.TurnDown(this.Down);
  },
  Down: function(e) {
	this.RepairY(e.clientY - this._sideUp, this._mxDownHeight);
	this.TurnUp(this.Up);
  },
  Right: function(e) {
	this.RepairX(e.clientX - this._sideLeft, this._mxRightWidth);
	this.TurnLeft(this.Left);
  },
  Left: function(e) {
	this.RepairX(this._sideRight - e.clientX, this._mxLeftWidth);
	this.RepairLeft();
	this.TurnRight(this.Right);
  },
  RightDown: function(e) {
	this.RepairAngle(
		e.clientX - this._sideLeft, this._mxRightWidth,
		e.clientY - this._sideUp, this._mxDownHeight
	);
	this.TurnLeft(this.LeftDown) || this.Scale || this.TurnUp(this.RightUp);
  },
  RightUp: function(e) {
	this.RepairAngle(
		e.clientX - this._sideLeft, this._mxRightWidth,
		this._sideDown - e.clientY, this._mxUpHeight
	);
	this.RepairTop();
	this.TurnLeft(this.LeftUp) || this.Scale || this.TurnDown(this.RightDown);
  },
  LeftDown: function(e) {
	this.RepairAngle(
		this._sideRight - e.clientX, this._mxLeftWidth,
		e.clientY - this._sideUp, this._mxDownHeight
	);
	this.RepairLeft();
	this.TurnRight(this.RightDown) || this.Scale || this.TurnUp(this.LeftUp);
  },
  LeftUp: function(e) {
	this.RepairAngle(
		this._sideRight - e.clientX, this._mxLeftWidth,
		this._sideDown - e.clientY, this._mxUpHeight
	);
	this.RepairTop(); this.RepairLeft();
	this.TurnRight(this.RightUp) || this.Scale || this.TurnDown(this.LeftDown);
  },
  RepairX: function(iWidth, mxWidth) {
	iWidth = this.RepairWidth(iWidth, mxWidth);
	if(this.Scale){
		var iHeight = this.RepairScaleHeight(iWidth);
		if(this.Max && iHeight > this._mxScaleHeight){
			iHeight = this._mxScaleHeight;
			iWidth = this.RepairScaleWidth(iHeight);
		}else if(this.Min && iHeight < this.minHeight){
			var tWidth = this.RepairScaleWidth(this.minHeight);
			if(tWidth < mxWidth){ iHeight = this.minHeight; iWidth = tWidth; }
		}
		this._styleHeight = iHeight;
		this._styleTop = this._scaleTop - iHeight / 2;
	}
	this._styleWidth = iWidth;
  },
  RepairY: function(iHeight, mxHeight){
	iHeight = this.RepairHeight(iHeight,mxHeight);
	if(this.Scale){
		var iWidth = this.RepairScaleWidth(iHeight);
		if(this.Max && iWidth > this._mxScaleWidth){
			iWidth = this._mxScaleWidth;
			iHeight = this.RepairScaleHeight(iWidth);
		}else if(this.Min && iWidth < this.minWidth){
			var tHeight = this.RepairScaleHeight(this.minWidth);
			if(tHeight < mxHeight){iWidth=this.minWidth;iHeight=tHeight;}
		}
		this._styleWidth = iWidth;
		this._styleLeft = this._scaleLeft - iWidth / 2;
	}
	this._styleHeight = iHeight;
  },
  RepairAngle: function(iWidth, mxWidth, iHeight, mxHeight) {
	iWidth = this.RepairWidth(iWidth, mxWidth);	
	if(this.Scale){
		iHeight = this.RepairScaleHeight(iWidth);
		if(this.Max && iHeight > mxHeight){
			iHeight = mxHeight;
			iWidth = this.RepairScaleWidth(iHeight);
		}else if(this.Min && iHeight < this.minHeight){
			var tWidth = this.RepairScaleWidth(this.minHeight);
			if(tWidth < mxWidth){ iHeight = this.minHeight; iWidth = tWidth; }
		}
	}else{
		iHeight = this.RepairHeight(iHeight, mxHeight);
	}
	this._styleWidth = iWidth;
	this._styleHeight = iHeight;
  },
  RepairTop: function() {
	this._styleTop = this._fixTop - this._styleHeight;
  },
  RepairLeft: function() {
	this._styleLeft = this._fixLeft - this._styleWidth;
  },
  RepairHeight: function(iHeight, mxHeight) {
	iHeight = Math.min(this.Max ? mxHeight : iHeight, iHeight);
	iHeight = Math.max(this.Min ? this.minHeight : iHeight, iHeight, 0);
	return iHeight;
  },
  RepairWidth: function(iWidth, mxWidth) {
	iWidth = Math.min(this.Max ? mxWidth : iWidth, iWidth);
	iWidth = Math.max(this.Min ? this.minWidth : iWidth, iWidth, 0);
	return iWidth;
  },
  RepairScaleHeight: function(iWidth) {
	return Math.max(Math.round((iWidth + this._borderX) / this.Ratio - this._borderY), 0);
  },
  RepairScaleWidth: function(iHeight) {
	return Math.max(Math.round((iHeight + this._borderY) * this.Ratio - this._borderX), 0);
  },
  TurnRight: function(fun) {
	if(!(this.Min || this._styleWidth)){
		this._fun = fun;
		this._sideLeft = this._sideRight;
		this.Max && this._mxSet();
		return true;
	}
  },
  TurnLeft: function(fun) {
	if(!(this.Min || this._styleWidth)){
		this._fun = fun;
		this._sideRight = this._sideLeft;
		this._fixLeft = this._styleLeft;
		this.Max && this._mxSet();
		return true;
	}
  },
  TurnUp: function(fun) {
	if(!(this.Min || this._styleHeight)){
		this._fun = fun;
		this._sideDown = this._sideUp;
		this._fixTop = this._styleTop;
		this.Max && this._mxSet();
		return true;
	}
  },
  TurnDown: function(fun) {
	if(!(this.Min || this._styleHeight)){
		this._fun = fun;
		this._sideUp = this._sideDown;
		this.Max && this._mxSet();
		return true;
	}
  },
  Stop: function() {
	removeEventHandler(document, "mousemove", this._fR);
	removeEventHandler(document, "mouseup", this._fS);
	if(isIE){
		removeEventHandler(this._obj, "losecapture", this._fS);
		this._obj.releaseCapture();
	}else{
		removeEventHandler(window, "blur", this._fS);
	}
  }
};