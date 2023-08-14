package dev.dayoung

enum class Command {
    CD, LS
}

class VFS {

    data class File(val name: String, val size: Int)
    data class Dir(val name: String, val parent: Dir? = null, val dirs: MutableList<Dir> = mutableListOf(), val files: MutableList<File> = mutableListOf())

    fun cd(path: String) {
        if(path == "..") {
            cwd = cwd.dropLast(1).toMutableList()
            cdl = currentParent.dirs
            cfl = currentParent.files
        } else {
            cwd.add(path)

        }
    }
    fun mkdir(name: String) {
        cdl.add(Dir(name, currentParent))
    }

    fun touch(name: String, size: Int) {
        cfl.add(File(name, size))
    }
    companion object {
        var cwd = mutableListOf("/")
        val fs = Dir("/", null)
        var cdl = fs.dirs
        var cfl = fs.files
        val currentParent = fs

    }
}
/*
class SevenPointTwo(sampleMode: Boolean) {
    private val content = Utils.readInputResource(sampleMode,"seven.txt")
    val fs = Dir("/", mutableListOf(), mutableListOf())
    class MyFile(val size: Int, val name: String) {
        override fun toString(): String {
            return "Name: $name Size: $size"
        }
    }
    class Dir(val name: String, val fileList: MutableList<MyFile> = mutableListOf(), val dirList : MutableList<Dir> = mutableListOf()) {
        override fun toString(): String {
            return "Dir $name (${fileList.size}) Files: $fileList (${dirList.size}) Dirs: $dirList"
        }
    }

    data class LsResult(val count: Int, val dirs : List<Dir>, val files : List<MyFile>)
    fun whatDo(line: String): Command {
        return when {
            line.startsWith("$ cd") -> Command.CD
            line.startsWith("$ ls") -> Command.LS
            else -> throw IllegalStateException(line)
        }
    }
    private fun readDirList(lines: List<String>, idx: Int) : LsResult {
        var readLines = 0
        val dirList = mutableListOf<Dir>()
        val fileList = mutableListOf<MyFile>()
        while(!lines[idx + readLines].startsWith("$")) {
            if(lines[idx + readLines].startsWith("dir")) {
                dirList.add(Dir(lines[idx + readLines].split(" ").last()))
            } else {
                val l = lines[idx + readLines].split(" ")
                val f =
                fileList.add(MyFile(l.first().toInt(),  l.last()))
            }
            readLines++
        }

        return LsResult(readLines, dirList , fileList)
    }
    fun parse(lines: List<String>) {
        var idx = 1;
        var currentDir = "/"
        while (idx <= lines.lastIndex) {
            val cmd = lines[idx]
            when(whatDo(cmd)) {
                Command.CD -> {
                }
                Command.LS-> {
                    val (count, dirs, files) = readDirList(lines, idx + 1)
                    idx += count

                }
            }
            idx++
        }
    }
    fun solve() {
        parse(content)
    }
}

 */
class Seven(sampleMode: Boolean = false) {
    class Directory(val name: String, val fileList: MutableList<MyFile>, val dirList: MutableList<Directory>) {
        override fun toString(): String {
            return "Dir $name (${fileList.size}) Files: $fileList (${dirList.size}) Dirs: $dirList"
        }
    }
    class MyFile(val size: Int, val name: String) {
        override fun toString(): String {
            return "Name: $name Size: $size"
        }
    }

    fun parseDir(parentDirName: String, dirContent: List<String>): Directory {
        val dirs = mutableListOf<Directory>()
        val files = mutableListOf<MyFile>()
        for(line in dirContent) {
            if(line.startsWith("dir")) {
                val (_, dirName) = line.split(" ")
                dirs.add(Directory(listOf(parentDirName, dirName).joinToString("/"), mutableListOf(), mutableListOf()))
            } else {
                val (size, filename) = line.split(" ")
                files.add(MyFile(size.toInt(), filename))
            }
        }
        return Directory(parentDirName, files, dirs)
    }
    fun parseDirListing(lines: List<String>): List<String> {
        val listing = mutableListOf<String>()
        for(line in lines) {
            if(!line.startsWith("$")) {
                listing.add(line)
            } else {
                break
            }
        }
        return listing
    }

    fun parseCdCommand(line: String): String {
        val (_, _, name) = line.split(" ")
        return name
    }

    fun whatDo(line: String): Command {
        return when {
            line.startsWith("$ cd") -> Command.CD
            line.startsWith("$ ls") -> Command.LS
            else -> throw IllegalStateException(line)
        }
    }

    private val content = Utils.readInputResource(sampleMode,"seven.txt")
    var currentDirectory = mutableListOf<String>()
    val directoryTree = Directory("/", mutableListOf(), mutableListOf())

    val directoryList = mutableListOf<Directory>()

    fun parse(lines: List<String>) {
        var currentIdx = 0
        while(currentIdx <= lines.lastIndex) {
            when(whatDo(lines[currentIdx])) {
                Command.LS -> {
                    val dirLines = parseDirListing(lines.subList(++currentIdx, lines.size))
                    val dirListing = parseDir(currentDirectory.joinToString("/"), dirLines)
                    directoryList.add(dirListing)
                    val toAdd = dirLines.size
                    currentIdx += toAdd
                }
                Command.CD -> {
                    val newDirectory = parseCdCommand(lines[currentIdx])
                    if(newDirectory == "..") {
                        currentDirectory = currentDirectory.dropLast(1).toMutableList()
                    } else {
                        currentDirectory.add(newDirectory)
                    }
                    currentIdx++
                }
            }
        }
    }

    fun parseDirList() {
        val tree = directoryList[0]
        for(dirs in tree.dirList) {
            directoryList.find { it.name == dirs.name }
            println(dirs)
        }
    }

    fun solve() {
        parse(content)
        parseDirList()
    }
}