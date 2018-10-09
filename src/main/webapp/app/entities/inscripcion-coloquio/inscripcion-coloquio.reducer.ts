import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IInscripcionColoquio, defaultValue } from 'app/shared/model/inscripcion-coloquio.model';

export const ACTION_TYPES = {
  FETCH_INSCRIPCIONCOLOQUIO_LIST: 'inscripcionColoquio/FETCH_INSCRIPCIONCOLOQUIO_LIST',
  FETCH_INSCRIPCIONCOLOQUIO: 'inscripcionColoquio/FETCH_INSCRIPCIONCOLOQUIO',
  CREATE_INSCRIPCIONCOLOQUIO: 'inscripcionColoquio/CREATE_INSCRIPCIONCOLOQUIO',
  UPDATE_INSCRIPCIONCOLOQUIO: 'inscripcionColoquio/UPDATE_INSCRIPCIONCOLOQUIO',
  DELETE_INSCRIPCIONCOLOQUIO: 'inscripcionColoquio/DELETE_INSCRIPCIONCOLOQUIO',
  RESET: 'inscripcionColoquio/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IInscripcionColoquio>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type InscripcionColoquioState = Readonly<typeof initialState>;

// Reducer

export default (state: InscripcionColoquioState = initialState, action): InscripcionColoquioState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_INSCRIPCIONCOLOQUIO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_INSCRIPCIONCOLOQUIO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_INSCRIPCIONCOLOQUIO):
    case REQUEST(ACTION_TYPES.UPDATE_INSCRIPCIONCOLOQUIO):
    case REQUEST(ACTION_TYPES.DELETE_INSCRIPCIONCOLOQUIO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_INSCRIPCIONCOLOQUIO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_INSCRIPCIONCOLOQUIO):
    case FAILURE(ACTION_TYPES.CREATE_INSCRIPCIONCOLOQUIO):
    case FAILURE(ACTION_TYPES.UPDATE_INSCRIPCIONCOLOQUIO):
    case FAILURE(ACTION_TYPES.DELETE_INSCRIPCIONCOLOQUIO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_INSCRIPCIONCOLOQUIO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_INSCRIPCIONCOLOQUIO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_INSCRIPCIONCOLOQUIO):
    case SUCCESS(ACTION_TYPES.UPDATE_INSCRIPCIONCOLOQUIO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_INSCRIPCIONCOLOQUIO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/inscripcion-coloquios';

// Actions

export const getEntities: ICrudGetAllAction<IInscripcionColoquio> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_INSCRIPCIONCOLOQUIO_LIST,
  payload: axios.get<IInscripcionColoquio>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IInscripcionColoquio> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_INSCRIPCIONCOLOQUIO,
    payload: axios.get<IInscripcionColoquio>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IInscripcionColoquio> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_INSCRIPCIONCOLOQUIO,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IInscripcionColoquio> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_INSCRIPCIONCOLOQUIO,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IInscripcionColoquio> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_INSCRIPCIONCOLOQUIO,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
