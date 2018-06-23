var defaultCountryName = "USD";
var jsonStream = null;
$(document).ready(function() {
	$(".table-responsive").hide();
	connect(defaultCountryName);

	$(".dropdown-item").click(function() {
		console.log($(this).html());
		$(".table-responsive").hide();
		$(".progress").show();
		var countryName = "";
		if ("USD - United States" === $(this).html()) {
			countryName = "USD";
		} else if ("AUD - Australia" === $(this).html()) {
			countryName = "AUD";
		}
		connect(countryName);
	});
});

function connect(countryname) {

	if(jsonStream!=null)
		jsonStream.close();
	jsonStream = new EventSource("http://localhost:8080/coins/"
			+ countryname)
	jsonStream.onmessage = function(e) {
		$(".progress").hide();
		$("table tbody").empty();
		$(".table-responsive").show();
		$("#tbodyid").empty();
		var message = JSON.parse(e.data);
		$.each(message, function(index, value) {
			console.log(value.name);
			console.log(value.price);
			var markup = "<tr><td>" + value.name + "</td><td>" + value.price
					+ "</td></tr>";
			$("table tbody").append(markup);
		});
	};

}
