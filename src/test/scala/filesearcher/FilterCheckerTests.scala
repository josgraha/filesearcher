package filesearcher

import org.scalatest.FlatSpec
import java.io.File

class FilterCheckerTests extends FlatSpec {
    "FilterChecker passed a list where one file matches the filter" should
    "return a list with that file" in {
        val matchingFile = new FileObject(new File("match"))
        val listOfFiles = List(new FileObject(new File("random")), matchingFile)
        val matchedFiles = FilterChecker("match") findMatchedFiles listOfFiles
        assert(matchedFiles == List(matchingFile))
    }
    
    "FilterChecker passed a list with a directory that matches the filter" should
    "omit the directory from the results" in {
        val listOfIOObjects = List(new FileObject(new File("random")), 
            new DirectoryObject(new File("match")))
        val matchedFiles = FilterChecker("match") findMatchedFiles listOfIOObjects
        assert(matchedFiles.length == 0)
    }
    
    "FilterChecker passed a file with content that matches the filter" should
    "return that the match succeeded" in {
        val contentMatchedCount = FilterChecker("app")
            .findMatchedContentCount(new File("./testfiles/app.data"))
        assert(contentMatchedCount == 3)
    }
    
    "FilterChecker passed a file with content that does not match the filter" should
    "return that the match failed" in {
        val contentMatchedCount = FilterChecker("app")
            .findMatchedContentCount(new File("./testfiles/readme.txt"))
        assert(contentMatchedCount == 0)
    }
}