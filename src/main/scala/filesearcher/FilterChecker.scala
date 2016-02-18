package filesearcher

import java.io.File
import scala.util.control.NonFatal

class FilterChecker(filter: String) {
    
    def matches(content : String) = content contains filter
    
    def findMatchedFiles(fileObjects : List[IOObject]) =
        for(iOObject <- fileObjects
            if(iOObject.isInstanceOf[FileObject])
            if(matches(iOObject.name)))
                yield iOObject
    
    def matchesFileContent(file: File) = {
        import scala.io.Source
        try {
            val fileSource = Source.fromFile(file)
            try
                fileSource.getLines() exists(line => matches(line))
            catch {
              case NonFatal(_) => false  
            }
            finally
                fileSource.close()
        } catch {
            case NonFatal(_) => false
        }
    }
}

object FilterChecker {
    def apply(filter: String) = new FilterChecker(filter)
}
