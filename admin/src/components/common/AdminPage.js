import classNames from 'classnames'
import SideBar from '../SideBar'
import Title from './Title'

const AdminPage = ({ pageName, children }) => {
  return (
    <div className={classNames('page')}>
      <SideBar currentPage={pageName} />
      <div className={classNames('body')}>
        <Title text={pageName} />
        <div className={classNames('content')}>{children}</div>
      </div>
    </div>
  )
}

export default AdminPage
