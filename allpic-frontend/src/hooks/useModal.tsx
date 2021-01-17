import {useState} from "react";

const useModal = (): UseModalType => {
  const [isShowing, setIsShowing] = useState(false);

  const toggle = () => {
    setIsShowing(!isShowing);
  }

  return {
    isShowing,
    toggle
  };
};

export interface UseModalType {
  isShowing: boolean,
  toggle: () => void
}

export default useModal;
