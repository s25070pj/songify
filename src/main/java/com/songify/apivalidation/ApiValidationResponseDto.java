package com.songify.apivalidation;

import java.util.List;

public record ApiValidationResponseDto(List<String> errors) {
}
