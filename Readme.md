Test task

Android application, that displays image feed from remote source(most recent Flickr photos).

- Displays list of images in a scrollable GridView
- Displays image title provided by feed on top of each image
- Downloads image list from online source (for example https://www.flickr.com/services/api/explore/flickr.photos.getRecent)
- Downloads each image in background, update UI when image is ready
- UI scroll smoothly
- Cancellation download requests when image is no longer needed (i.e. user scrolls away from it)
- Implemented parallel download limit while keeping application responsible
- Implemented page-based list retrieval (download next page when reaching end of list)
- Cached downloaded images on disk
- Applied blur to image segment under image title

For detail description see <a href="../../raw/master/data/task.txt"><b>task.txt</b></a> file in data folder

How it works (YouTube demo):

Also available here http://youtu.be/EexxX-yw8QQ

<a href="http://www.youtube.com/watch?feature=player_embedded&v=EexxX-yw8QQ
" target="_blank"><img src="http://img.youtube.com/vi/EexxX-yw8QQ/0.jpg"
alt="YouTube demo" width="640" height="480" border="10" /></a>