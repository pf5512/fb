
function resetTabs(obj) {
	$(obj).parent().parent().next("div").find("div.tab").hide();
	$(obj).parent().parent().find("a").removeClass("current");
}
function loadTab() {
	$(".tabContent > .tab").hide();
	$(".tabs").each(function () {
		$(this).find("li:first a").addClass("current");
	});
	$(".tabContent").each(function () {
		$(this).find("div:first").fadeIn();
	});
	$(".tabs a").on("click", function (e) {
		e.preventDefault();
		if ($(this).attr("class") == "current") {
			return;
		} else {
			resetTabs(this);
			$(this).addClass("current");
			$($(this).attr("id")).fadeIn();
		}
	});
}

