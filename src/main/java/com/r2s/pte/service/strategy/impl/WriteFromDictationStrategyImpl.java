package com.r2s.pte.service.strategy.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.r2s.pte.common.Text;
import com.r2s.pte.dto.QuestionDTO;
import com.r2s.pte.dto.QuestionResponseUserDTO;
import com.r2s.pte.dto.QuestionSolutionDTO;
import com.r2s.pte.dto.ScoreResponseDTO;
import com.r2s.pte.dto.UserScoreDTO;
import com.r2s.pte.service.strategy.QuestionScoreStrategy;
import com.r2s.pte.util.HandleGeneral;
@Component("WriteFromDictationStrategy")
public class WriteFromDictationStrategyImpl implements QuestionScoreStrategy {
	@Autowired
	private HandleGeneral handleGeneral;
	@Override
	public Integer doScore(QuestionDTO questionDTO, QuestionResponseUserDTO questionResponseUser,
			QuestionSolutionDTO dto) {
		int first = 0;
		List<QuestionSolutionDTO> questionSolutionDTOs = questionDTO.getSolutions();
		QuestionSolutionDTO questionSolutionDTO = (QuestionSolutionDTO) questionSolutionDTOs.toArray()[first];
		String result = questionSolutionDTO.getValueText();
		if(result == null)
			result = questionSolutionDTO.getExplanation();
		String response = questionResponseUser.getValueText();
		return doScore(result, response);
	}
	private Integer doScore(String result, String response)
	{
		Integer score = 0;
		result = handleGeneral.normalizeString(result);
		result = result.toLowerCase();
		response = handleGeneral.normalizeString(response);
		response = response.toLowerCase();
		List<Text> textResults = handleGeneral.setListText(result);
		List<Text> textResponses = handleGeneral.setListText(response);
		for (Text textResult : textResults) {
			for (Text textResponse : textResponses) {
				if (textResponse.getValue().equals(textResult.getValue())) {
					if (textResponse.getCount() == textResult.getCount())
						score += textResponse.getCount();
					else {
						int getMin = textResponse.getCount() > textResult.getCount() ? textResult.getCount(): textResponse.getCount();
						score += getMin;
					}
				}
			}
		}
		return score;
	}
	@Override
	public ScoreResponseDTO save(List<UserScoreDTO> userScoreDTOs) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
