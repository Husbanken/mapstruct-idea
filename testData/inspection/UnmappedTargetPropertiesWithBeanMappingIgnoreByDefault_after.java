/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.example.data.UnmappedTargetPropertiesData.Target;
import org.example.data.UnmappedTargetPropertiesData.Source;

@Mapper
interface SingleMappingMapper {

    @Mapping(target = "moreTarget", source = "")
    @Mapping(target = "moreTarget", ignore = true)
    @Mapping(target = "testName", source = "name")
    Target map(Source source);
}

@Mapper
interface SingleMappingMapperWithIgnoreByDefault {

    @Mapping(target = "testName", source = "name")
    @BeanMapping(ignoreByDefault = true)
    Target map(Source source);
}

@Mapper
interface UpdateMapper {

    @Mapping(target = "testName", source = "")
    @Mapping(target = "testName", ignore = true)
    @Mapping(target = "moreTarget", source = "moreSource")
    void update(@MappingTarget Target target, Source source);
}

@Mapper
interface UpdateMapperWithIgnoreByDefault {

    @Mapping(target = "moreTarget", source = "moreSource")
    @BeanMapping(ignoreByDefault = true)
    void update(@MappingTarget Target target, Source source);
}
