import sys
from cccat_core import Cccat

predefined_args = {
    "-b" : 1,
    "-n" : 1
}

def getFilenameAndArgs(args):
    filenames = []
    arguments = {}
    if(len(args)>0):
        for arg in args:
            if(arg.startswith('-')):
                arguments[arg] = 1
            else:
                filenames.append(arg)
    return filenames,arguments


args = sys.argv[1:]
filenames,arguments = getFilenameAndArgs(args)
Cccat(filenames,arguments, len(args) == 0 or args[0].startswith('-') )



