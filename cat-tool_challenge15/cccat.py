import sys


def getFilenameAndArgs(args):
    filename = args[0]
    arguments = {}
    if(len(args)>1):
        for arg in args[1:]:
            arguments[arg] = 1

    return filename,arguments

from cccat_core import Cccat
args = sys.argv[1:]

if len(args) == 0:
    print("No arguments provided")
else:
    filename,arguments = getFilenameAndArgs(args)
    Cccat(filename,arguments)



