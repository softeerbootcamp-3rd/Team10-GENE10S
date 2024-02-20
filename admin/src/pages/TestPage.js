import classNames from "classnames";
import SideBar from "../components/SideBar";

export default function TestPage() {
    return (
      <div className={classNames('page')}>
        <SideBar currentPage={'reservation'} />
        <div className={classNames('body')}>

        </div>
      </div>
    );
  }
  