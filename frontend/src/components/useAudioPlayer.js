import { useEffect, useRef } from 'react';

const useAudioPlayer = (src, loopMode) => {
  const audioRef = useRef(new Audio());

  useEffect(() => {
    if (src) {
      const audioElement = audioRef.current;
      audioElement.src = src;
      audioElement.loop = loopMode;
      audioElement.play();
    }
  }, [src, loopMode]);

  return audioRef;
};

export default useAudioPlayer;