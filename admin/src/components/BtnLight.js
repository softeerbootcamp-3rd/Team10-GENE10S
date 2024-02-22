import classNames from "classnames";

export function BtnLight({ text, onClick }) {
  return (
    <div className={classNames("btn-light")} onClick={onClick}>
      <span>{text}</span>
    </div>
  );
}
