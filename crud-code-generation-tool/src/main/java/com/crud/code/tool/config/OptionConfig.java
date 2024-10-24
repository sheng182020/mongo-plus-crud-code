package com.crud.code.tool.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class OptionConfig {


    Boolean isHasEnum;

    Boolean isHasPage;

    String outputPath;

    Boolean isHasList;

    Boolean isHasObjectId;

    // String handleTypePath;
    // String baseEntityPath;
    // String baseRequestPath;
    // String baseResponsePath;

}
