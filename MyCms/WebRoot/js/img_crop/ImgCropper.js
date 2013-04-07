var ImgCropper=Class.create();
ImgCropper.prototype={
  initialize:function(container,handle,url,options){
	this._Container=$(container);
	this._layHandle=$(handle);
	this.Url=url;	
	this._layBase = this._Container.appendChild(document.createElement("img"));
	this._layCropper = this._Container.appendChild(document.createElement("img"));
	this._layCropper.onload = Bind(this, this.SetPos);
	this._tempImg = document.createElement("img");
	this._tempImg.onload = Bind(this, this.SetSize);	
	this.SetOptions(options);	
	this.Opacity = Math.round(this.options.Opacity);
	this.Color = this.options.Color;
	this.Scale = !!this.options.Scale;
	this.Ratio = Math.max(this.options.Ratio, 0);
	this.Width = Math.round(this.options.Width);
	this.Height = Math.round(this.options.Height);
	var oPreview = $(this.options.Preview);
	if(oPreview){
		oPreview.style.position = "relative";
		oPreview.style.overflow = "hidden";
		this.viewWidth = Math.round(this.options.viewWidth);
		this.viewHeight = Math.round(this.options.viewHeight);
		this._view = oPreview.appendChild(document.createElement("img"));
		this._view.style.position = "absolute";
		this._view.onload = Bind(this, this.SetPreview);
	}
	this._drag = new Drag(this._layHandle, { Limit: true, onMove: Bind(this, this.SetPos), Transparent: true });
	this.Resize = !!this.options.Resize;
	if(this.Resize){
		var op = this.options, _resize = new Resize(this._layHandle, { Max: true, onResize: Bind(this, this.SetPos) });
		op.RightDown && (_resize.Set(op.RightDown, "right-down"));
		op.LeftDown && (_resize.Set(op.LeftDown, "left-down"));
		op.RightUp && (_resize.Set(op.RightUp, "right-up"));
		op.LeftUp && (_resize.Set(op.LeftUp, "left-up"));
		op.Right && (_resize.Set(op.Right, "right"));
		op.Left && (_resize.Set(op.Left, "left"));
		op.Down && (_resize.Set(op.Down, "down"));
		op.Up && (_resize.Set(op.Up, "up"));
		this.Min = !!this.options.Min;
		this.minWidth = Math.round(this.options.minWidth);
		this.minHeight = Math.round(this.options.minHeight);
		this._resize = _resize;
	}
	this._Container.style.position = "relative";
	this._Container.style.overflow = "hidden";
	this._layHandle.style.zIndex = 200;
	this._layCropper.style.zIndex = 100;
	this._layBase.style.position = this._layCropper.style.position = "absolute";
	this._layBase.style.top = this._layBase.style.left = this._layCropper.style.top = this._layCropper.style.left = 0;//对齐
	this.Init();
  },
  SetOptions: function(options) {
    this.options = {
		Opacity:	50,
		Color:		"",
		Width:		0,
		Height:		0,
		Resize:		false,
		Right:		"",
		Left:		"",
		Up:			"",
		Down:		"",
		RightDown:	"",
		LeftDown:	"",
		RightUp:	"",
		LeftUp:		"",
		Min:		false,
		minWidth:	50,
		minHeight:	50,
		Scale:		false,
		Ratio:		0,
		Preview:	"",
		viewWidth:	0,
		viewHeight:	0
    };
    Extend(this.options, options || {});
  },
  Init: function() {
	this.Color && (this._Container.style.backgroundColor = this.Color);
	this._tempImg.src = this._layBase.src = this._layCropper.src = this.Url;
	if(isIE){
		this._layBase.style.filter = "alpha(opacity:" + this.Opacity + ")";
	} else {
		this._layBase.style.opacity = this.Opacity / 100;
	}
	this._view && (this._view.src = this.Url);
	if(this.Resize){
		with(this._resize){
			Scale = this.Scale; Ratio = this.Ratio; Min = this.Min; minWidth = this.minWidth; minHeight = this.minHeight;
		}
	}
  },
  SetPos: function() {
	var p = this.GetPos();
	this._layCropper.style.clip = "rect(" + p.Top + "px " + (p.Left + p.Width) + "px " + (p.Top + p.Height) + "px " + p.Left + "px)";
	this.SetPreview();
  },
  SetPreview: function() {
	if(this._view){
		var p = this.GetPos(), s = this.GetSize(p.Width, p.Height, this.viewWidth, this.viewHeight), scale = s.Height / p.Height;
		var pHeight = this._layBase.height * scale, pWidth = this._layBase.width * scale, pTop = p.Top * scale, pLeft = p.Left * scale;
		with(this._view.style){
			width = pWidth + "px"; height = pHeight + "px"; top = - pTop + "px "; left = - pLeft + "px";
			clip = "rect(" + pTop + "px " + (pLeft + s.Width) + "px " + (pTop + s.Height) + "px " + pLeft + "px)";
		}
	}
  },
  SetSize: function() {
	var s = this.GetSize(this._tempImg.width, this._tempImg.height, this.Width, this.Height);
	this._layBase.style.width = this._layCropper.style.width = s.Width + "px";
	this._layBase.style.height = this._layCropper.style.height = s.Height + "px";
	this._drag.mxRight = s.Width; this._drag.mxBottom = s.Height;
	if(this.Resize){ this._resize.mxRight = s.Width; this._resize.mxBottom = s.Height; }
  },
  GetPos: function() {
	with(this._layHandle){
		return { Top: offsetTop, Left: offsetLeft, Width: offsetWidth, Height: offsetHeight };
	}
  },
  GetSize: function(nowWidth, nowHeight, fixWidth, fixHeight) {
	var iWidth = nowWidth, iHeight = nowHeight, scale = iWidth / iHeight;
	if(fixHeight){ iWidth = (iHeight = fixHeight) * scale; }
	if(fixWidth && (!fixHeight || iWidth > fixWidth)){ iHeight = (iWidth = fixWidth) / scale; }
	return { Width: iWidth, Height: iHeight };
  }
};