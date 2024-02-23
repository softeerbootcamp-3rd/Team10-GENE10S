import classNames from 'classnames'
import Title from '../common/Title'

const ModalFrame = ({ visible, title, children }) => {
  return (
    <div className={classNames('modal-frame', { visible: visible })}>
      <div className={classNames('modal')}>
        <Title text={title} />
        <div className={classNames('content')}>
          {children}
        </div>
      </div>
    </div>
  )
}

export default ModalFrame
