function exit-if-not-exists() {
 if [ ! -f $1 ]; then
          echo "Command Failed:Could not find file $1";
          exit -1;
  fi
}
