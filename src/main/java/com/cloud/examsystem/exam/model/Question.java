package com.cloud.examsystem.exam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question implements Serializable {
    private List<Option> optionList = new ArrayList<>();
    private String description="";
    private Integer number;
    private Integer score = 0;

    public Question(int optionCount, int index){
        for (int i= 0; i<optionCount;i++){
            this.number=index;
            this.optionList.add(new Option(i));
        }
    }

    public Option getOptionByOptionNumber(int optionIndex){
        for (Option o:optionList){
            if (o.getIndex() == optionIndex){
                return o;

            }

        }
        return null;
    }

    public boolean isEquals(Question target){
        if (this.optionList.size()!= target.getOptionList().size()){
            return false;
        }
        else{
            for(int i = 0; i < optionList.size(); i++){
                if (this.getOptionByOptionNumber(i).getCorrect()!=target.getOptionByOptionNumber(i).getCorrect()){
                    return false;
                }
            }
        }
        return true;
    }

    public Question convertDto(){
        List<Option> optionTmp = new ArrayList<>();

        for (Option option:optionList){
            Option optionDto = option;
            optionDto.setCorrect(false);
            optionTmp.add(optionDto);
        }
        Question q = new Question();
        q.setNumber(this.getNumber());
        q.setDescription(this.getDescription());
        q.setOptionList(optionTmp);
        q.setScore(this.score);
        return q;


    }

}
