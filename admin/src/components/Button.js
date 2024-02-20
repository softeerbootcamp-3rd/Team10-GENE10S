import classNames from "classnames";

export default function BtnDark({ text }) {
    return (
        <div className={classNames('btn-dark')}>
            <span>{text}</span>
        </div>
    );
}

export function BtnLight({ text }) {
    return (
        <div className={classNames('btn-light')}>
            <span>{text}</span>
        </div>
    );
}
