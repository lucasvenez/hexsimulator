function Hexagon(canvas, j) {
	this.ctx = canvas;
	this.color = "#FFFFFF";
	this.lineColor = "#000000";
	this.lineWeight = 1;
	this.size = 20;
	this.x = this.size + 5;
	this.y = this.size + 5;
	this.fillText = j;
}

Hexagon.prototype.draw = function() {
	
	this.ctx.beginPath();
	this.ctx.moveTo (this.x +  this.size * Math.sin(0), this.y +  this.size *  Math.cos(0));          
 
	for (var i = 1; i <= 6; i++) {
		this.ctx.lineTo (
			this.x + this.size * Math.sin(i * 2 * Math.PI / 6), 
			this.y + this.size * Math.cos(i * 2 * Math.PI / 6));
	}
 
	this.ctx.strokeStyle = this.lineColor;
	this.ctx.fillStyle = this.color;
	this.ctx.lineWidth = this.lineWeight;
	this.ctx.stroke();
	this.ctx.fill();
	this.ctx.fillStyle = "black";
	this.ctx.font = "10pt sans-serif";
	this.ctx.fillText(this.fillText, this.x, this.y);
};

Hexagon.prototype.setLineColor = function(lineColor) {
	this.lineColor = lineColor;
};

Hexagon.prototype.setColor = function(color) {
	this.color = color;
};

Hexagon.prototype.setCenter = function(x, y) {
	this.x = x;
	this.y = y;	
};

Hexagon.prototype.setSize = function(size) {
	this.size = size;
	this.x = this.size + 5;
	this.y = this.size + 5;
};