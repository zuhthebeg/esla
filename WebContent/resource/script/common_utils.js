
function get_string_trim( string )
{
    return string.replace(/(^\s*)|(\s*$)/g, "");
}

function get_string_length( string )
{
    var count = 0;

    var tmp_str = new String(string);
    var temp = tmp_str.length;

    var onechar;

    for ( k=0; k<temp; k++ )
    {
        onechar = tmp_str.charAt(k);

        if (escape(onechar).length > 4)
        {
            count += 2;
        }
        else
        {
            count += 1;
        }
    }
    return count;
}

function get_comment_cut_length( string, _max_len )
{
    var count = 0;
    var cut_count = 0;
    var tmp_str = new String(string);
    var temp = tmp_str.length;

    var onechar;

    var max_len = _max_len-1;

    for ( k=0; k<temp; k++ )
    {
        onechar = tmp_str.charAt(k);
        if (escape(onechar).length > 4)
        {
            if(count<max_len){
                cut_count +=1;
            }
            count += 2;
        }
        else
        {
            if(count<max_len){
                cut_count +=1;
            }
            count += 1;
        }
    }
    return cut_count;
}

function str_limit_check( obj, _maxlen, targetid )
{
	comment = obj.value;
	max_len = _maxlen;

	content_length = get_string_length( comment );

	if( typeof( document.all[targetid] ) == "object" )
	{
		document.all[targetid].innerHTML = "("+content_length+"/"+max_len+")";
	}

	if( content_length > max_len )
	{ 
		msg = '최대' + max_len + ' Byte 입력바람.';
		alert( msg );
		obj.value = comment.substr(0, max_len);
		cut_length = get_comment_cut_length( comment, max_len );
		obj.value = comment.substr(0, cut_length);
		content_length = get_string_length( obj.value ); 
		if( typeof( document.all[targetid] ) == "object" )
		{
			document.all[targetid].innerHTML = "("+content_length+"/"+max_len+")";
		}
	}
}
