package filesearcher

import org.scalatest.FlatSpec
import java.io.File

class MatcherTests extends FlatSpec {
    "Matcher that is passed a file matching the filter" should
    "return a list with that file name" in {
        val matcher = new Matcher("fake", "fakePath")
        val results = matcher.execute()
        assert(results == List(("fakePath", None)))
    }
    
    "Matcher that is passed a directory with a file matching the filter" should
    "return a list with that file name" in {
        val matcher = new Matcher("txt", new File("./testfiles").getCanonicalPath())
        val results = matcher.execute()
        assert(results == List(("readme.txt", None)))
    }
    
    "Matcher that is not passed a root file location" should
    "use the current location" in {
        val matcher = new Matcher("filter")
        assert(matcher.rootLocation == new File(".").getCanonicalPath())
    }
    
    "Matcher with a sub folder checking matching a root location with two subtree files matching" should
    "return a list with those names" in {
        val searchSubDirectories = true
        val matcher = new Matcher("txt", new File("./testfiles").getCanonicalPath(), 
            searchSubDirectories)
        val results = matcher.execute()
        assert(results == List(("notes.txt", None), ("readme.txt", None)))
    }
    
    "Matcher given a path that has one file that matches that file filter and content filter" should
    "return a list with that file name" in {
        val matcher = new Matcher("data", new File("./testfiles").getCanonicalPath(), true,
            Some("app"))
        val matchedFiles = matcher.execute()
        assert(matchedFiles == List(("app.data", Some(3))))
    }
    
    "Matcher given a path that has no file that matches file filter and content filter" should
    "return an empty list" in {
        val matcher = new Matcher("txt", new File("./testfiles").getCanonicalPath(), true,
            Some("app"))
        val matchedFiles = matcher.execute()
        assert(matchedFiles == List())
    }
}