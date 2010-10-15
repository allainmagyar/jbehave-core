package org.jbehave.core.embedder;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.jbehave.core.model.Description;
import org.jbehave.core.model.Meta;
import org.jbehave.core.model.Narrative;
import org.jbehave.core.model.Scenario;
import org.jbehave.core.model.Story;
import org.jbehave.core.model.StoryMap;
import org.junit.Test;

public class StoryMapperBehaviour {

    @Test
    public void shouldMapStoriesAllowedByFilter() throws Throwable {
        // Given
        Meta meta1 = mock(Meta.class, "meta1");
        Story story1 = new Story("/path/to/story1", Description.EMPTY, meta1, Narrative.EMPTY, asList(new Scenario("scenario1", meta1)));
        Meta meta2 = mock(Meta.class, "meta2");
        Story story2 = new Story("/path/to/story2", Description.EMPTY, meta2, Narrative.EMPTY, asList(new Scenario("scenario2", meta2)));
        MetaFilter filter = mock(MetaFilter.class);
        String filterAsString = "-some property";
        
        // When
        StoryMapper mapper = new StoryMapper();
        when(filter.allow(meta1)).thenReturn(false);
        when(filter.allow(meta2)).thenReturn(true);
        when(filter.allow(Meta.inherit(meta1, meta1))).thenReturn(false);
        when(filter.allow(Meta.inherit(meta2, meta2))).thenReturn(true);
        when(filter.asString()).thenReturn(filterAsString);
        mapper.map(story1, filter);
        mapper.map(story2, filter);

        // Then
        List<StoryMap> storyMaps = mapper.getStoryMaps();
        assertThat(storyMaps.size(), equalTo(1));
        StoryMap storyMap = storyMaps.get(0);
        assertThat(storyMap.getMetaPattern(), equalTo(filterAsString));
        assertThat(storyMap.getStories().get(0).getPath(), equalTo(story2.getPath()));
        assertThat(storyMap.getStoryNames(), equalTo(asList("story2")));
        assertThat(storyMap.getStoryPaths(), equalTo(asList(story2.getPath())));
    }
  
}
