package wethinkcode.schedule.transfer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalTime;

/**
 * TODO: javadoc SlotDO
 */
public class SlotDO
{
    private LocalTime start;

    private LocalTime end;

    public SlotDO(){
    }

    @JsonCreator
    public SlotDO(
        @JsonProperty( value = "from" ) LocalTime from,
        @JsonProperty( value = "to" ) LocalTime to ){
        start = from;
        end = to;
    }

    public LocalTime getStart(){
        return start;
    }

    public LocalTime getEnd(){
        return end;
    }

}
