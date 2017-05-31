/**
 * Created by E.Marouane on 29/05/2017.
 */
jQuery(document).ready(function($) {
    $("#search-form").submit(function(event) {

        // Prevent the form from submitting via the browser.
        event.preventDefault();
        searchViaAjax();

    });
});

function searchAjax() {
    var data = {}
    data["query"] = $("#query").val();

    $.ajax({
        type : "POST",
        contentType : "application/json",
        url : "${home}search/api/getSearchResult",
        data : JSON.stringify(data),
        dataType : 'json',
        timeout : 100000,
        success : function(data) {
            console.log("SUCCESS: ", data);
            display(data);
        },
        error : function(e) {
            console.log("ERROR: ", e);
            display(e);
        },
        done : function(e) {
            console.log("DONE");
        }
    });
}