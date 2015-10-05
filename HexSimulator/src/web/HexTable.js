function HexTable(n) {
	this.n = n;	
	this.peaces = [];
};

HexTable.prototype.draw = function() {
	
	var canvas = document.getElementById("hextable");
	var ctx = canvas.getContext("2d");
	ctx.canvas.width  = window.innerWidth * .95;
	ctx.canvas.height = window.innerHeight * .95;
	
	var size = 30;
	
	for (var i = 0; i < this.n; i++) {
	
		for (var j = 0; j < this.n; j++) {
			
			var h = new Hexagon(ctx, this.n * i + j);
			this.peaces[this.peaces.length] = h;
			h.setSize(size);
			var x = size * (j + 1) * 2 + size * (i + 1),
			    y = size * (i + 1) * 2;
			h.setCenter(x * .92, y * .80);
			h.draw();
		}
	}
};

HexTable.prototype.getPeace = function(i) {
	return this.peaces[i];
};